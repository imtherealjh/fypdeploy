package com.uow.FYP_23_S1_11.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.uow.FYP_23_S1_11.constraints.OnCreate;
import com.uow.FYP_23_S1_11.constraints.OnUpdate;
import com.uow.FYP_23_S1_11.domain.Appointment;
import com.uow.FYP_23_S1_11.domain.Clinic;
import com.uow.FYP_23_S1_11.domain.Doctor;
import com.uow.FYP_23_S1_11.domain.EducationalMaterial;
import com.uow.FYP_23_S1_11.domain.request.BookUpdateAppointmentRequest;
import com.uow.FYP_23_S1_11.domain.request.ClinicAndDoctorFeedbackRequest;
import com.uow.FYP_23_S1_11.domain.request.DoctorAvailableRequest;
import com.uow.FYP_23_S1_11.domain.request.PatientFeedbackClinicRequest;
import com.uow.FYP_23_S1_11.domain.request.PatientFeedbackDoctorRequest;
import com.uow.FYP_23_S1_11.domain.request.QueueRequest;
import com.uow.FYP_23_S1_11.service.PatientService;
import com.uow.FYP_23_S1_11.domain.MailDetails;
import com.uow.FYP_23_S1_11.domain.request.MailRequest;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

@RestController
@RequestMapping(value = "/api/patient", produces = { MediaType.APPLICATION_JSON_VALUE })
@Validated
@PreAuthorize("hasAuthority('PATIENT')")
@SecurityRequirement(name = "bearerAuth")
public class PatientController {
    @Autowired
    private PatientService patientService;

    @GetMapping("/getPastAppointment")
    public ResponseEntity<List<?>> getPastAppointments() {
        return ResponseEntity.ok(patientService.getPastAppointments());
    }

    @GetMapping("/getUpcomingAppointment")
    public ResponseEntity<List<?>> getUpcomingAppointments() {
        return ResponseEntity.ok(patientService.getUpcomingAppointments());
    }

    @GetMapping("/getClinicsBySpecialty")
    public ResponseEntity<List<?>> getClinicBySpecialty(@RequestParam @NotEmpty String specialty) {
        return ResponseEntity.ok(patientService.getAllClinicBySpecialty(specialty));
    }

    @PostMapping("/getDoctorAvailability")
    public ResponseEntity<List<Appointment>> getAvailableAppointment(@RequestBody DoctorAvailableRequest req) {
        return ResponseEntity.ok(patientService.getDoctorAvailableAppointment(req));
    }

    @GetMapping("/getAppointmentById")
    public ResponseEntity<Appointment> getAppointmentById(@RequestParam Integer id) {
        return ResponseEntity.ok(patientService.getAppointmentById(id));
    }

    @Validated(OnCreate.class)
    @PostMapping("/bookAppointment")
    public ResponseEntity<Boolean> bookAppointment(@Valid @RequestBody BookUpdateAppointmentRequest bookApptReq) {
        return ResponseEntity.ok(patientService.bookAvailableAppointment(bookApptReq));
    }

    @Validated(OnUpdate.class)
    @PutMapping("/updateAppointment")
    public ResponseEntity<Boolean> updateAppointment(
            @Valid @RequestBody BookUpdateAppointmentRequest updateApptReq) {
        return ResponseEntity.ok(patientService.updateAppointment(updateApptReq));
    }

    @DeleteMapping("/deleteAppointment")
    public ResponseEntity<Boolean> deleteAppointment(@RequestParam Integer apptId) {
        return ResponseEntity.ok(patientService.deleteAppointment(apptId));
    }

    //
    @GetMapping("/getAllEduMaterial")
    public ResponseEntity<List<EducationalMaterial>> getAllEduMaterial() {
        return ResponseEntity.ok(patientService.getAllEduMaterial());
    }

    @GetMapping("/getEduMaterialById")
    public ResponseEntity<EducationalMaterial> getEduMaterialById(@RequestParam Integer id) {
        return ResponseEntity.ok(patientService.getEduMaterialById(id));
    }

    @PostMapping("/sendMail")
    public String sendMail(@RequestBody MailRequest details) {
        String status = patientService.sendSimpleMail(details);

        return status;
    }

    // @PostMapping("/sendMailWithAttachment")
    // public String sendMailWithAttachment(@RequestBody MailRequest details) {
    // String status = patientService.sendMailWithAttachment(details);

    // return status;
    // }

    @PostMapping("/insertClinicAndDoctorFeedback")
    public ResponseEntity<Boolean> insertClinicAndDoctorFeedback(
            @Valid @RequestBody ClinicAndDoctorFeedbackRequest clinicAndDoctorFeedbackRequest) {
        return ResponseEntity.ok(patientService.insertClinicAndDoctorFeedback(clinicAndDoctorFeedbackRequest));
    }

    @PostMapping("/updateClinicFeedback")
    public ResponseEntity<Boolean> updateClinicFeedback(@RequestParam Integer clinicFeedbackId,
            @Valid @RequestBody PatientFeedbackClinicRequest updateClinicFeedbackRequest) {
        return ResponseEntity.ok(patientService.updateClinicFeedback(clinicFeedbackId, updateClinicFeedbackRequest));
    }

    @PostMapping("/updateDoctorFeedback")
    public ResponseEntity<Boolean> updateDoctorFeedback(@RequestParam Integer doctorFeedbackId,
            @Valid @RequestBody PatientFeedbackDoctorRequest updateDoctorFeedbackRequest) {
        return ResponseEntity.ok(patientService.updateDoctorFeedback(doctorFeedbackId, updateDoctorFeedbackRequest));
    }

    @DeleteMapping("/deleteClinicFeedback")
    public ResponseEntity<Boolean> deleteClinicFeedback(@RequestParam Integer clinicFeedbackId) {
        return ResponseEntity.ok(patientService.deleteClinicFeedback(clinicFeedbackId));
    }

    @DeleteMapping("/deleteDoctorFeedback")
    public ResponseEntity<Boolean> deleteDoctorFeedback(@RequestParam Integer doctorFeedbackId) {
        return ResponseEntity.ok(patientService.deleteDoctorFeedback(doctorFeedbackId));
    }

    @PostMapping("/insertQueueNumber")
    public ResponseEntity<Boolean> insertQueueNumber(
            @Valid @RequestBody QueueRequest request) {
        return ResponseEntity.ok(patientService.insertQueueNumber(request));
    }

    @GetMapping("/getByQueueId")
    public ResponseEntity<?> getByQueueId(@RequestParam Integer queueId) {
        return ResponseEntity.ok(patientService.getByQueueId(queueId));
    }
}
