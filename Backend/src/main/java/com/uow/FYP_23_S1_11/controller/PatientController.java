package com.uow.FYP_23_S1_11.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
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
import com.uow.FYP_23_S1_11.domain.request.BookUpdateAppointmentRequest;
import com.uow.FYP_23_S1_11.domain.request.ClinicAndDoctorFeedbackRequest;
import com.uow.FYP_23_S1_11.domain.request.DoctorAvailableRequest;
import com.uow.FYP_23_S1_11.domain.request.RegisterPatientRequest;
import com.uow.FYP_23_S1_11.domain.request.SearchLocReq;
import com.uow.FYP_23_S1_11.service.AppointmentService;
import com.uow.FYP_23_S1_11.service.PatientService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping(value = "/api/patient", produces = { MediaType.APPLICATION_JSON_VALUE })
@Validated
@PreAuthorize("hasAuthority('PATIENT')")
@SecurityRequirement(name = "bearerAuth")
public class PatientController {
    @Autowired
    private AppointmentService apptService;
    @Autowired
    private PatientService patientService;

    @GetMapping("/getPatientProfile")
    public ResponseEntity<?> getPatientProfile() {
        return ResponseEntity.ok(patientService.getPatientProfile());
    }

    @GetMapping("/getAllAppointments")
    public ResponseEntity<?> getPastAppointments() {
        return ResponseEntity.ok(patientService.getAllAppointments());
    }

    @GetMapping("/getClinicsBySpecialty")
    public ResponseEntity<List<?>> getClinicBySpecialty(@RequestParam @NotEmpty String specialty) {
        return ResponseEntity.ok(patientService.getAllClinicBySpecialty(specialty));
    }

    @PostMapping("/getClinicsBySpecLoc")
    public ResponseEntity<?> getClinicsBySpecLoc(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size, @RequestBody @Valid SearchLocReq searchLocReq) {
        return ResponseEntity.ok(patientService.getAllClinicSpecLoc(searchLocReq, PageRequest.of(page, size)));
    }

    @PostMapping("/getDoctorAvailability")
    public ResponseEntity<List<Appointment>> getAvailableAppointment(@RequestBody @Valid DoctorAvailableRequest req) {
        return ResponseEntity.ok(patientService.getDoctorAvailableAppointment(req));
    }

    @GetMapping("/getAppointmentById")
    public ResponseEntity<Appointment> getAppointmentById(@RequestParam Integer id) {
        return ResponseEntity.ok(apptService.getAppointmentById(id));
    }

    @Validated(OnCreate.class)
    @PostMapping("/bookAppointment")
    public ResponseEntity<Boolean> bookAppointment(@Valid @RequestBody BookUpdateAppointmentRequest bookApptReq) {
        return ResponseEntity.ok(apptService.bookAvailableAppointment(bookApptReq));
    }

    @Validated(OnUpdate.class)
    @PutMapping("/updateAppointment")
    public ResponseEntity<Boolean> updateAppointment(
            @RequestBody @Valid BookUpdateAppointmentRequest updateApptReq) {
        return ResponseEntity.ok(apptService.updateAppointment(updateApptReq));
    }

    @DeleteMapping("/deleteAppointment")
    public ResponseEntity<Boolean> deleteAppointment(@RequestParam @NotNull Integer apptId) {
        return ResponseEntity.ok(apptService.deleteAppointment(apptId));
    }

    @Validated(OnUpdate.class)
    @PutMapping("/updateProfile")
    public ResponseEntity<?> updateProfile(@RequestBody @Valid RegisterPatientRequest patientReq) {
        return ResponseEntity.ok(patientService.updateProfile(patientReq));
    }

    @PostMapping("/insertFeedback")
    public ResponseEntity<Boolean> insertClinicAndDoctorFeedback(
            @Valid @RequestBody ClinicAndDoctorFeedbackRequest clinicAndDoctorFeedbackRequest) {
        return ResponseEntity.ok(patientService.insertClinicAndDoctorFeedback(clinicAndDoctorFeedbackRequest));
    }

}
