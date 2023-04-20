package com.uow.FYP_23_S1_11.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uow.FYP_23_S1_11.Constants;
import com.uow.FYP_23_S1_11.domain.Appointment;
import com.uow.FYP_23_S1_11.domain.Clinic;
import com.uow.FYP_23_S1_11.domain.Doctor;
import com.uow.FYP_23_S1_11.domain.Patient;
import com.uow.FYP_23_S1_11.domain.PatientFeedback;
import com.uow.FYP_23_S1_11.domain.PatientFeedbackClinic;
import com.uow.FYP_23_S1_11.domain.PatientFeedbackDoctor;
import com.uow.FYP_23_S1_11.domain.Specialty;
import com.uow.FYP_23_S1_11.domain.UserAccount;
import com.uow.FYP_23_S1_11.domain.request.BookUpdateAppointmentRequest;
import com.uow.FYP_23_S1_11.domain.request.DoctorAvailableRequest;
import com.uow.FYP_23_S1_11.enums.EAppointmentStatus;
import com.uow.FYP_23_S1_11.repository.AppointmentRepository;
import com.uow.FYP_23_S1_11.repository.ClinicRepository;
import com.uow.FYP_23_S1_11.repository.DoctorRepository;
import com.uow.FYP_23_S1_11.repository.PatientFeedbackRepository;
import com.uow.FYP_23_S1_11.repository.PatientRepository;
import com.uow.FYP_23_S1_11.repository.SpecialtyRepository;
import com.uow.FYP_23_S1_11.repository.EduMaterialRepository;
import com.uow.FYP_23_S1_11.repository.PatientFeedbackClinicRepository;
import com.uow.FYP_23_S1_11.repository.PatientFeedbackDoctorRepository;
import com.uow.FYP_23_S1_11.domain.EducationalMaterial;

import com.uow.FYP_23_S1_11.domain.MailDetails;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
//import javax.mail.MessagingException;
//import javax.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import com.uow.FYP_23_S1_11.domain.request.MailRequest;

import com.uow.FYP_23_S1_11.domain.request.PatientFeedbackClinicRequest;
import com.uow.FYP_23_S1_11.domain.request.PatientFeedbackDoctorRequest;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class PatientServiceImpl implements PatientService {
    @Autowired
    private PatientFeedbackRepository patientFeedbackRepo;
    @Autowired
    private PatientRepository patientRepo;

    @Autowired
    private ClinicRepository clinicRepo;
    @Autowired
    private DoctorRepository doctorRepo;
    @Autowired
    private AppointmentRepository apptRepo;
    @Autowired
    private EduMaterialRepository eduMaterialRepo;
    @Autowired
    private PatientFeedbackClinicRepository patientFeedbackClinicRepo;
    @Autowired
    private PatientFeedbackDoctorRepository patientFeedbackDoctorRepo;

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
    public List<Doctor> getAllDoctorsByClinicSpecialty(Integer clincId, String specialty) {
        return doctorRepo.findByClinicSpecialty(clincId, specialty);
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
            return new Appointment();
        }
    }

    @Override
    public Boolean bookAvailableAppointment(BookUpdateAppointmentRequest bookApptReq) {
        try {
            UserAccount currentUser = Constants.getAuthenticatedUser();
            Patient patient = currentUser.getPatient();
            // TODO: need to check if current appointment is booked
            Optional<Appointment> apptOptional = apptRepo.findById(bookApptReq.getApptId());
            if (apptOptional.isEmpty()) {
                throw new IllegalArgumentException("No available appointment...");
            }
            Appointment appt = apptOptional.get();
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

    @Override
    public Boolean updateAppointment(Integer originalApptId, BookUpdateAppointmentRequest updateApptReq) {
        try {
            UserAccount currentUser = Constants.getAuthenticatedUser();
            Patient patient = currentUser.getPatient();
            Optional<Appointment> optionalOrigAppt = apptRepo.findById(originalApptId);
            if (optionalOrigAppt.isEmpty()) {
                throw new IllegalArgumentException("Appointment not found...");
            }

            Appointment origAppt = optionalOrigAppt.get();
            if (patient.getPatientId() == origAppt.getApptPatient().getPatientId()) {
                if (originalApptId == updateApptReq.getApptId()) {
                    origAppt.setDescription(updateApptReq.getDescription());
                    apptRepo.save(origAppt);
                } else {
                    origAppt.setStatus(EAppointmentStatus.AVAILABLE);
                    origAppt.setDescription(null);
                    origAppt.setApptPatient(null);
                    apptRepo.save(origAppt);
                    bookAvailableAppointment(updateApptReq);
                }
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
            Optional<Appointment> apptOptional = apptRepo.findById(apptId);
            if (apptOptional.isEmpty()) {
                throw new IllegalArgumentException("No available appointment...");
            }
            Appointment appt = apptOptional.get();
            if (patient.getPatientId() == appt.getApptPatient().getPatientId()) {
                appt.setStatus(EAppointmentStatus.AVAILABLE);
                appt.setDescription(null);
                appt.setApptPatient(null);
                apptRepo.save(appt);
            }
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
            // Below code for sending email test when implementing in a function, to
            // delete...
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            // to get current user's email
            UserAccount currentUser = Constants.getAuthenticatedUser();

            mailMessage.setFrom(sender);
            mailMessage.setTo(currentUser.getPatient().getEmail());
            String template = "Hello " +
                    String.valueOf(currentUser.getPatient().getName()) +
                    ",\n\n\ttesting send message\n" +
                    "\tnewline test\n" +
                    "\ttest sent educational material details\n" +
                    "\tContent of Material:\n" +
                    String.valueOf(materialOptional.get().getContent()) +
                    "\n\nBest Regards\n" +
                    "Good Doctor Team";
            mailMessage.setText(template);
            mailMessage.setSubject("View Educational Material: " + materialOptional.get().getTitle());

            javaMailSender.send(mailMessage);
            //
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
    public Boolean insertClinicFeedback(PatientFeedbackClinicRequest request) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            PatientFeedbackClinic patientFeedbackClinic = (PatientFeedbackClinic) mapper.convertValue(request,
                    PatientFeedbackClinic.class);
            patientFeedbackClinicRepo.save(patientFeedbackClinic);
            return true;

        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    @Override
    public Boolean insertDoctorFeedback(PatientFeedbackDoctorRequest request) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            PatientFeedbackDoctor patientFeedbackDoctor = (PatientFeedbackDoctor) mapper.convertValue(request,
                    PatientFeedbackDoctor.class);
            patientFeedbackDoctorRepo.save(patientFeedbackDoctor);
            return true;

        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

}
