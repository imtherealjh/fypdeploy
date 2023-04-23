package com.uow.FYP_23_S1_11.service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.uow.FYP_23_S1_11.Constants;
import com.uow.FYP_23_S1_11.domain.Appointment;
import com.uow.FYP_23_S1_11.domain.Patient;
import com.uow.FYP_23_S1_11.domain.PatientFeedbackClinic;
import com.uow.FYP_23_S1_11.domain.PatientFeedbackDoctor;
import com.uow.FYP_23_S1_11.domain.Queue;
import com.uow.FYP_23_S1_11.domain.UserAccount;
import com.uow.FYP_23_S1_11.domain.request.BookUpdateAppointmentRequest;
import com.uow.FYP_23_S1_11.domain.request.ClinicAndDoctorFeedbackRequest;
import com.uow.FYP_23_S1_11.domain.request.DoctorAvailableRequest;
import com.uow.FYP_23_S1_11.enums.EAppointmentStatus;
import com.uow.FYP_23_S1_11.repository.AppointmentRepository;
import com.uow.FYP_23_S1_11.repository.ClinicRepository;
import com.uow.FYP_23_S1_11.repository.QueueRepository;
import com.uow.FYP_23_S1_11.repository.EduMaterialRepository;
import com.uow.FYP_23_S1_11.repository.PatientFeedbackClinicRepository;
import com.uow.FYP_23_S1_11.repository.PatientFeedbackDoctorRepository;
import com.uow.FYP_23_S1_11.domain.EducationalMaterial;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import com.uow.FYP_23_S1_11.domain.request.MailRequest;

import com.uow.FYP_23_S1_11.domain.request.PatientFeedbackClinicRequest;
import com.uow.FYP_23_S1_11.domain.request.PatientFeedbackDoctorRequest;
import com.uow.FYP_23_S1_11.domain.request.QueueRequest;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class PatientServiceImpl implements PatientService {
    @Autowired
    private ClinicRepository clinicRepo;
    @Autowired
    private AppointmentRepository apptRepo;
    @Autowired
    private EduMaterialRepository eduMaterialRepo;
    @Autowired
    private PatientFeedbackClinicRepository patientFeedbackClinicRepo;
    @Autowired
    private PatientFeedbackDoctorRepository patientFeedbackDoctorRepo;
    @Autowired
    private QueueRepository queueRepo;

    //
    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String sender;
    //

    @Override
    public List<?> getUpcomingAppointments() {
        UserAccount userAccount = Constants.getAuthenticatedUser();
        return apptRepo.getUpcomingAppointments(userAccount.getPatient());
    }

    @Override
    public List<?> getPastAppointments() {
        UserAccount userAccount = Constants.getAuthenticatedUser();
        return apptRepo.getPastAppointments(userAccount.getPatient());
    }

    @Override
    public List<?> getAllClinicBySpecialty(String specialty) {
        return clinicRepo.findBySpecialty(specialty);
    }

    @Override
    public List<Appointment> getDoctorAvailableAppointment(DoctorAvailableRequest req) {
        LocalDate parseDate = LocalDate.parse(req.getDate());
        return apptRepo.findAvailableApptByDoctorAndDay(req.getDoctorId(), EAppointmentStatus.AVAILABLE, parseDate);
    }

    @Override
    public Appointment getAppointmentById(Integer apptId) {
        try {
            Optional<Appointment> apptOptional = apptRepo.findById(apptId);
            if (apptOptional.isEmpty()) {
                throw new IllegalArgumentException("Appointment does not exist");
            }
            return apptOptional.get();
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    @Override
    public Boolean bookAvailableAppointment(BookUpdateAppointmentRequest bookApptReq) {
        try {
            UserAccount currentUser = Constants.getAuthenticatedUser();
            Patient patient = currentUser.getPatient();
            Appointment appt = getAppointmentById(bookApptReq.getApptId());
            // Ensure that the appointment is available and ensure that the
            // appointment date is not after today
            // ensure that the doctor is not suspended
            if (appt == null ||
                    appt.getStatus() != EAppointmentStatus.AVAILABLE ||
                    appt.getApptDate().isBefore(LocalDate.now())) {
                throw new IllegalArgumentException("Appointment cannot be booked...");
            }
            appt.setStatus(EAppointmentStatus.BOOKED);
            appt.setDescription(bookApptReq.getDescription());
            appt.setApptPatient(patient);
            apptRepo.save(appt);
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    private void removeAppointment(Appointment appointment) {
        appointment.setStatus(EAppointmentStatus.AVAILABLE);
        appointment.setDescription(null);
        appointment.setApptPatient(null);
        apptRepo.save(appointment);
    }

    @Override
    public Boolean updateAppointment(BookUpdateAppointmentRequest updateApptReq) {
        try {
            UserAccount currentUser = Constants.getAuthenticatedUser();
            Patient patient = currentUser.getPatient();

            Appointment origAppt = getAppointmentById(updateApptReq.getOriginalApptId());
            // validate if is the actual person updating the appointment
            // and ensure that older appointments cannot be updated
            if (origAppt == null ||
                    patient.getPatientId() != origAppt.getApptPatient().getPatientId() ||
                    origAppt.getStatus() != EAppointmentStatus.BOOKED ||
                    origAppt.getApptDate().isBefore(LocalDate.now())) {
                throw new IllegalArgumentException("Appointment cannot be updated...");
            }

            if (origAppt.getAppointmentId() == updateApptReq.getApptId()) {
                origAppt.setDescription(updateApptReq.getDescription());
                apptRepo.save(origAppt);
            } else {
                bookAvailableAppointment(updateApptReq);
                removeAppointment(origAppt);
            }

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Boolean deleteAppointment(Integer apptId) {
        try {
            UserAccount currentUser = Constants.getAuthenticatedUser();
            Patient patient = currentUser.getPatient();

            Appointment origAppt = getAppointmentById(apptId);
            if (origAppt == null || patient.getPatientId() != origAppt.getApptPatient().getPatientId()) {
                throw new IllegalArgumentException("Appointment cannot be removed...");
            }

            removeAppointment(origAppt);
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    @Override
    public List<EducationalMaterial> getAllEduMaterial() {
        return eduMaterialRepo.findAll();
    }

    @Override
    public EducationalMaterial getEduMaterialById(Integer materialId) {
        try {
            Optional<EducationalMaterial> materialOptional = eduMaterialRepo.findById(materialId);
            if (materialOptional.isEmpty()) {
                throw new IllegalArgumentException("Educational material does not exist..");
            }
            return materialOptional.get();
        } catch (Exception e) {
            System.out.println(e);
            return new EducationalMaterial();
        }
    }

    // Test email function (this is a page)
    // To implement to function, just copy code and paste into functions,
    // change variables accordingly (require recipient's email in string)
    @Override
    public String sendSimpleMail(MailRequest details) {

        // Try block to check for exceptions
        try {
            // Setting up current user
            UserAccount currentUser = Constants.getAuthenticatedUser();
            // String recipientEmail = currentUser.getPatient().getEmail();

            // Creating a simple mail message
            SimpleMailMessage mailMessage = new SimpleMailMessage();

            // Setting up necessary details
            mailMessage.setFrom(sender);

            // dynamic allocation of recipient
            // mailMessage.setTo(details.getRecipient());
            // mailMessage.setTo(currentUser.getPatient().getEmail()) //to set based on
            // roles

            // multiple recipient test
            mailMessage.setTo(new String[] { "soosteven96@gmail.com", "animecrazysteve@gmail.com" });

            // setting the message and subject by defining a template
            String template = "Hello " +
                    String.valueOf(currentUser.getUsername()) +
                    ",\n\n\ttesting sent message\n" +
                    String.valueOf(details.getMsgBody()) +
                    "\n\tnewline test\n\n" +
                    "Best Regards\n" +
                    "Good Doctor Team";
            mailMessage.setText(template);
            mailMessage.setSubject(details.getSubject());

            // Sending the mail
            javaMailSender.send(mailMessage);
            return "Mail Sent Successfully...";
        }

        // Catch block to handle the exceptions
        catch (Exception e) {
            System.out.println(e);
            return "Error While Sending Mail..";
        }
    }

    // Not working
    // @Override
    // public String sendMailWithAttachment(MailRequest details) {
    // // Creating a mime message
    // MimeMessage mimeMessage = javaMailSender.createMimeMessage();
    // MimeMessageHelper mimeMessageHelper;

    // try {
    // // Setting multipart as true for attachments to
    // // be send
    // mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
    // mimeMessageHelper.setFrom(sender);
    // mimeMessageHelper.setTo(details.getRecipient());
    // mimeMessageHelper.setText(details.getMsgBody());
    // mimeMessageHelper.setSubject(details.getSubject());

    // // Adding the attachment
    // FileSystemResource file = new FileSystemResource(new
    // File(details.getAttachment()));

    // mimeMessageHelper.addAttachment("test.txt", file);

    // // Sending the mail
    // javaMailSender.send(mimeMessage);
    // return "Mail sent Successfully";
    // }

    // // Catch block to handle MessagingException
    // catch (MessagingException e) {

    // // Display message when exception occurred
    // return "Error while sending mail!!!";
    // }
    // }

    @Override
    public Boolean insertClinicAndDoctorFeedback(ClinicAndDoctorFeedbackRequest request) {
        try {

            PatientFeedbackClinic patientFeedbackClinic = new PatientFeedbackClinic();

            patientFeedbackClinic.setClinicFeedbackId(request.getFeedbackId());
            patientFeedbackClinic.setFeedback(request.getClinicFeedback());
            patientFeedbackClinic.setRatings(request.getClinicRatings());

            patientFeedbackClinicRepo.save(patientFeedbackClinic);

            PatientFeedbackDoctor patientFeedbackDoctor = new PatientFeedbackDoctor();

            patientFeedbackDoctor.setDoctorFeedbackId(request.getFeedbackId());
            patientFeedbackDoctor.setFeedback(request.getDoctorFeedback());
            patientFeedbackDoctor.setRatings(request.getDoctorRatings());

            patientFeedbackDoctorRepo.save(patientFeedbackDoctor);

            return true;

        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    @Override
    public Boolean updateClinicFeedback(Integer clinicFeedbackId,
            PatientFeedbackClinicRequest updateClinicFeedbackRequest) {
        Optional<PatientFeedbackClinic> patientFeedbackClinicOptional = patientFeedbackClinicRepo
                .findById(clinicFeedbackId);
        if (patientFeedbackClinicOptional.isEmpty()) {
            throw new IllegalArgumentException("Clinic feedback does not exist...");
        }
        PatientFeedbackClinic patientFeedbackClinic = patientFeedbackClinicOptional.get();
        patientFeedbackClinic.setRatings(updateClinicFeedbackRequest.getRatings());
        patientFeedbackClinic.setFeedback(updateClinicFeedbackRequest.getFeedback());
        patientFeedbackClinicRepo.save(patientFeedbackClinic);
        return true;
    }

    @Override
    public Boolean updateDoctorFeedback(Integer doctorFeedbackId,
            PatientFeedbackDoctorRequest updateDoctorFeedbackRequest) {
        Optional<PatientFeedbackDoctor> patientFeedbackDoctorOptional = patientFeedbackDoctorRepo
                .findById(doctorFeedbackId);
        if (patientFeedbackDoctorOptional.isEmpty()) {
            throw new IllegalArgumentException("Doctor feedback does not exist...");
        }
        PatientFeedbackDoctor patientFeedbackDoctor = patientFeedbackDoctorOptional.get();
        patientFeedbackDoctor.setRatings(updateDoctorFeedbackRequest.getRatings());
        patientFeedbackDoctor.setFeedback(updateDoctorFeedbackRequest.getFeedback());
        patientFeedbackDoctorRepo.save(patientFeedbackDoctor);
        return true;
    }

    @Override
    public Boolean deleteClinicFeedback(Integer clinicFeedbackId) {
        Optional<PatientFeedbackClinic> patientFeedbackClinicOptional = patientFeedbackClinicRepo
                .findById(clinicFeedbackId);
        if (patientFeedbackClinicOptional.isEmpty()) {
            throw new IllegalArgumentException("Clinic feedback does not exist...");
        }
        PatientFeedbackClinic patientFeedbackClinic = patientFeedbackClinicOptional.get();
        patientFeedbackClinicRepo.delete(patientFeedbackClinic);
        return true;
    }

    @Override
    public Boolean deleteDoctorFeedback(Integer doctorFeedbackId) {
        Optional<PatientFeedbackDoctor> patientFeedbackDoctorOptional = patientFeedbackDoctorRepo
                .findById(doctorFeedbackId);
        if (patientFeedbackDoctorOptional.isEmpty()) {
            throw new IllegalArgumentException("Doctor feedback does not exist...");
        }
        PatientFeedbackDoctor patientFeedbackDoctor = patientFeedbackDoctorOptional.get();
        patientFeedbackDoctorRepo.delete(patientFeedbackDoctor);
        return true;
    }

    @Override
    public Boolean insertQueueNumber(QueueRequest request) {
        Integer appointmentId = request.getCheckAppointmentId();
        Optional<Appointment> appointmentOptional = apptRepo
                .findById(appointmentId);
        Appointment appointment = appointmentOptional.get();
        LocalDate date1 = appointment.getApptDate();
        LocalDate date2 = LocalDate.now();
        if (date1 == date2) {
            ObjectMapper mapper = new ObjectMapper();
            Queue queue = (Queue) mapper.convertValue(request,
                    Queue.class);
            queueRepo.save(queue);
            return true;
        } else {
            throw new IllegalArgumentException("Your appointment is not today...");
        }
    }

    @Override
    public List<Queue> getByQueueId(Integer queueId) {
        List<Queue> queue = queueRepo
                .findByQueueId(queueId);
        if (queue.isEmpty() == false) {
            return queue;
        } else {
            throw new IllegalArgumentException("Queue number not found...");
        }
    }
}
