package com.uow.FYP_23_S1_11.config;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.uow.FYP_23_S1_11.domain.Appointment;
import com.uow.FYP_23_S1_11.domain.Clinic;
import com.uow.FYP_23_S1_11.domain.Doctor;
import com.uow.FYP_23_S1_11.domain.DoctorSchedule;
import com.uow.FYP_23_S1_11.enums.EAppointmentStatus;
import com.uow.FYP_23_S1_11.enums.EWeekdays;
import com.uow.FYP_23_S1_11.repository.AppointmentRepository;
import com.uow.FYP_23_S1_11.repository.ClinicRepository;
import com.uow.FYP_23_S1_11.repository.DoctorScheduleRepository;
import com.uow.FYP_23_S1_11.service.EmailService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableScheduling
@Slf4j
public class ScheduleTaskService {
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private ClinicRepository clinicRepo;
    @Autowired
    private DoctorScheduleRepository doctorScheduleRepo;
    @Autowired
    private AppointmentRepository apptRepo;
    @Autowired
    private EmailService emailService;

    @Value("${spring.mail.username}")
    private String sender;

    @Scheduled(cron = "0 0 0 * * *")
    @Async
    public void removeExistingAppointment() {
        List<Appointment> oldAppts = apptRepo.findByOldAvailableAppt();
        apptRepo.deleteAll(oldAppts);
        log.info("Successfully removed all old appointment");
    }

    @Scheduled(cron = "10 0 0 * * *")
    @Async
    public void execute() {
        List<LocalDate> dateRanges = LocalDate.now().datesUntil(LocalDate.now().plusDays(14))
                .collect(Collectors.toList());
        for (LocalDate date : dateRanges) {
            DayOfWeek dow = date.getDayOfWeek();
            String output = dow.getDisplayName(TextStyle.FULL, Locale.US);
            EWeekdays day = EWeekdays.valueOf(output.toUpperCase());

            List<Clinic> clinics = clinicRepo.findByClinicsWithSchedule(date, day);
            if (clinics.size() > 0) {
                List<Appointment> appointmentList = new ArrayList<Appointment>();
                for (Clinic clinic : clinics) {
                    List<Doctor> clinicDoctor = clinic.getDoctor();
                    LocalTime duration = clinic.getApptDuration();
                    for (Doctor doctor : clinicDoctor) {
                        List<DoctorSchedule> schedule = doctorScheduleRepo.findByDoctorAndDay(doctor, day);
                        List<Appointment> timeslots = new ArrayList<>();
                        for (DoctorSchedule doctorSchedule : schedule) {
                            LocalTime newTime = doctorSchedule.getStartTime();
                            while (newTime.compareTo(doctorSchedule.getEndTime()) < 0) {
                                timeslots.add(Appointment
                                        .builder()
                                        .status(EAppointmentStatus.AVAILABLE)
                                        .apptDoctor(doctor)
                                        .apptClinic(doctor.getDoctorClinic())
                                        .apptDate(date)
                                        .apptTime(newTime)
                                        .build());
                                newTime = newTime.plusHours(duration.getHour())
                                        .plusMinutes(duration.getMinute());
                            }
                        }
                        appointmentList.addAll(timeslots);
                    }
                    apptRepo.saveAll(appointmentList);
                    log.info("Added appointments for " + clinic.getClinicName() + " at " + LocalDate.now());
                }
            }
        }
    }

    @Scheduled(cron = "59 59 23 * * *")
    @Async
    public void sendReminderEmail() {
        TypedQuery<Appointment> query = entityManager.createQuery(
                "SELECT a FROM Appointment a WHERE a.apptPatient IS NOT NULL AND a.status = :status AND (a.apptDate = :date1 OR a.apptDate = :date2)",
                Appointment.class);
        query.setParameter("date1", LocalDate.now().plusDays(1));
        query.setParameter("date2", LocalDate.now().plusDays(7));
        query.setParameter("status", EAppointmentStatus.BOOKED);

        String content = "Dear [[name]],<br>"
                + "This is a reminder for your appointment...<br><br>"
                + "<b>Appointment Details:</b> <br>"
                + "<b>Clinic Name:</b> [[CLINICNAME]]<br>"
                + "<b>Doctor Name:</b> [[DOCTORNAME]]<br>"
                + "<b>Appointment Date:</b> [[APPTDATE]]<br>"
                + "<b>Appointment Time:</b> [[APPTTIME]]<br><br>"
                + "Thank you,<br>"
                + "GoDoctor Team.<br>"
                + "**This is an auto-generated email. Please do not reply directly to this email.**";

        for (Appointment appt : query.getResultList()) {
            try {
                content = content.replace("[[name]]", appt.getApptPatient().getName());
                content = content.replace("[[CLINICNAME]]", appt.getApptDoctor().getDoctorClinic().getClinicName());
                content = content.replace("[[DOCTORNAME]]", appt.getApptDoctor().getName());
                content = content.replace("[[APPTDATE]]", appt.getApptDate().toString());
                content = content.replace("[[APPTTIME]]", appt.getApptTime().toString());
                emailService.sendEmail(sender, "GoDoctor",
                        appt.getApptPatient().getEmail(), "Appointment Reminder...", content);
                log.info("Appointment Reminder successfully sent: {}", LocalDate.now() + " " + LocalTime.now());
            } catch (Exception e) {
                log.error("Appointment Reminder failed to send: {}", LocalDate.now() + " " + LocalTime.now());
            }
        }

    }
}
