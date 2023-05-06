package com.uow.FYP_23_S1_11.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

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

import com.uow.FYP_23_S1_11.constraints.OnStaffUpdate;
import com.uow.FYP_23_S1_11.domain.Appointment;
import com.uow.FYP_23_S1_11.domain.request.BookUpdateAppointmentRequest;
import com.uow.FYP_23_S1_11.domain.request.DoctorAvailableRequest;
import com.uow.FYP_23_S1_11.domain.request.PatientMedicalRecordsRequest;
import com.uow.FYP_23_S1_11.service.AppointmentService;
import com.uow.FYP_23_S1_11.service.PatientService;
import com.uow.FYP_23_S1_11.service.StaffService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping(value = "/api/staff", produces = { MediaType.APPLICATION_JSON_VALUE })
@Validated
@PreAuthorize("hasAnyAuthority('DOCTOR', 'NURSE', 'FRONT_DESK')")
@SecurityRequirement(name = "bearerAuth")
public class StaffController {
    @Autowired
    private StaffService staffService;
    @Autowired
    private AppointmentService apptService;
    @Autowired
    private PatientService patientService;

    @GetMapping("/getPatientsByDate")
    public ResponseEntity<Object> getPatientsByDate(@RequestParam @NotNull LocalDate apptDate) {
        return ResponseEntity.ok(staffService.getPatientsByDate(apptDate));
    }

    @GetMapping("/getAllPatients")
    public ResponseEntity<List<?>> getAllPatients() {
        return ResponseEntity.ok(staffService.getAllPatients());
    }

    @GetMapping("/getAppointmentDetails")
    public ResponseEntity<Map<?, ?>> getAppointmentDetails(@RequestParam @NotNull Integer patientId) {
        return ResponseEntity.ok(staffService.getAppointmentDetails(patientId));
    }

    @GetMapping("/getAppointmentByDate")
    public ResponseEntity<?> getAppointmentByDate(@RequestParam @NotNull LocalDate date) {
        return ResponseEntity.ok(staffService.getAppointmentByDate(date));
    }

    @PostMapping("/getDoctorAvailability")
    public ResponseEntity<List<Appointment>> getAvailableAppointment(@RequestBody @Valid DoctorAvailableRequest req) {
        return ResponseEntity.ok(patientService.getDoctorAvailableAppointment(req));
    }

    @PreAuthorize("hasAnyAuthority('FRONT_DESK', 'NURSE')")
    @Validated(OnStaffUpdate.class)
    @PutMapping("/updateAppointment")
    public ResponseEntity<?> updateAppointment(@RequestBody @Valid BookUpdateAppointmentRequest apptReq) {
        return ResponseEntity.ok(apptService.updateAppointment(apptReq));
    }

    @PreAuthorize("hasAnyAuthority('FRONT_DESK', 'NURSE')")
    @DeleteMapping("/deleteAppointment")
    public ResponseEntity<?> deleteAppointment(@RequestParam @NotNull Integer apptId) {
        return ResponseEntity.ok(apptService.deleteAppointment(apptId));
    }

    @PreAuthorize("hasAnyAuthority('DOCTOR', 'NURSE')")
    @PutMapping("/updateMedicalRecords")
    public ResponseEntity<Boolean> updateMedicalRecords(
            @Valid @RequestBody PatientMedicalRecordsRequest patientMedicalRecordsRequest) {
        return ResponseEntity.ok(staffService.updateMedicalRecords(patientMedicalRecordsRequest));
    }
}
