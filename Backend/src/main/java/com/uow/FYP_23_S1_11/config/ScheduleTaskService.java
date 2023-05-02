package com.uow.FYP_23_S1_11.config;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.uow.FYP_23_S1_11.domain.Appointment;
import com.uow.FYP_23_S1_11.domain.Doctor;
import com.uow.FYP_23_S1_11.enums.EAppointmentStatus;
import com.uow.FYP_23_S1_11.repository.DoctorRepository;
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
    private DoctorRepository doctorRepository;
    @Autowired
    private EmailService emailService;
    @Value("${spring.mail.username}")
    private String sender;

    @Scheduled(cron = "30 * * * * *")
    @Async
    public void execute() {

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
