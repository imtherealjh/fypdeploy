package com.uow.FYP_23_S1_11.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.uow.FYP_23_S1_11.domain.Appointment;
import com.uow.FYP_23_S1_11.domain.Clinic;
import com.uow.FYP_23_S1_11.domain.Doctor;
import com.uow.FYP_23_S1_11.domain.Specialty;
import com.uow.FYP_23_S1_11.domain.request.BookUpdateAppointmentRequest;
import com.uow.FYP_23_S1_11.service.PatientService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

@Validated
@RestController
@RequestMapping(value = "/api/patient", produces = { MediaType.APPLICATION_JSON_VALUE })
@SecurityRequirement(name = "bearerAuth")
public class PatientController {
    @Autowired
    private PatientService patientService;

    @GetMapping("/getAllSpecialty")
    public ResponseEntity<List<Specialty>> getAllSpecialty() {
        return ResponseEntity.ok(patientService.getAllSpecialty());
    }

    @GetMapping("/getClinicsBySpecialty")
    public ResponseEntity<List<Clinic>> getClinicBySpecialty(@RequestParam String specialty) {
        return ResponseEntity.ok(patientService.getAllClinicBySpecialty(specialty));
    }

    @GetMapping("/getDoctorsByClinicSpecialty")
    public ResponseEntity<List<Doctor>> getDoctorsByClinicSpecialty(@RequestParam Integer clinicId,
            @RequestParam String specialty) {
        return ResponseEntity.ok(patientService.getAllDoctorsByClinicSpecialty(clinicId, specialty));
    }

    @GetMapping("/getDoctorAvailability")
    public ResponseEntity<List<Appointment>> getAvailableAppointment(@RequestParam Integer doctorId,
            @RequestParam String date) {
        return ResponseEntity.ok(patientService.getDoctorAvailableAppointment(doctorId, date));
    }

    @GetMapping("/getAllAppointments")
    public ResponseEntity<List<Appointment>> getAllAppointments() {
        return ResponseEntity.ok(patientService.getBookedAppointments());
    }

    @GetMapping("/getAppointmentById")
    public ResponseEntity<Appointment> getAppointmentById(@RequestParam Integer id) {
        return ResponseEntity.ok(patientService.getAppointmentById(id));
    }

    @PostMapping("/bookAppointment")
    public ResponseEntity<Boolean> bookAppointment(@Valid @RequestBody BookUpdateAppointmentRequest bookApptReq) {
        return ResponseEntity.ok(patientService.bookAvailableAppointment(bookApptReq));
    }

    @PostMapping("/updateAppointment")
    public ResponseEntity<Boolean> updateAppointment(@RequestParam Integer apptId,
            @RequestBody BookUpdateAppointmentRequest updateApptReq) {
        return ResponseEntity.ok(patientService.updateAppointment(apptId, updateApptReq));
    }

    @DeleteMapping("/deleteAppointment")
    public ResponseEntity<Boolean> deleteAppointment(@RequestParam Integer apptId) {
        return ResponseEntity.ok(patientService.deleteAppointment(apptId));
    }

}
