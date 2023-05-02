package com.uow.FYP_23_S1_11.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.uow.FYP_23_S1_11.constraints.OnCreate;
import com.uow.FYP_23_S1_11.constraints.OnStaffUpdate;
import com.uow.FYP_23_S1_11.domain.PatientFeedbackClinic;
import com.uow.FYP_23_S1_11.domain.request.GenerateAppointmentRequest;
import com.uow.FYP_23_S1_11.domain.request.RegisterDoctorRequest;
import com.uow.FYP_23_S1_11.domain.request.RegisterFrontDeskRequest;
import com.uow.FYP_23_S1_11.domain.request.RegisterNurseRequest;
import com.uow.FYP_23_S1_11.service.ClinicOwnerService;
import com.uow.FYP_23_S1_11.service.StaffService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping(value = "/api/clinicOwner", produces = { MediaType.APPLICATION_JSON_VALUE })
@Validated
@PreAuthorize("hasAuthority('CLINIC_OWNER')")
@SecurityRequirement(name = "bearerAuth")
public class ClinicOwnerController {
    @Autowired
    private ClinicOwnerService clincOwnerService;
    @Autowired
    private StaffService staffService;

    @GetMapping("/getVisitingPatients")
    public ResponseEntity<?> getAllVisitingPatientByDate(@NotNull @RequestParam LocalDate apptDate) {
        return ResponseEntity.ok(staffService.getPatientsByDate(apptDate));
    }

    @GetMapping("/getAllStaffs")
    public ResponseEntity<List<?>> getAllStaffs() {
        return ResponseEntity.ok(clincOwnerService.getAllStaffs());
    }

    @GetMapping("/getAllDoctors")
    public ResponseEntity<List<?>> getAllDoctors() {
        return ResponseEntity.ok(clincOwnerService.getAllDoctors());
    }

    @GetMapping("/getDoctorById")
    public ResponseEntity<?> getDoctorById(@RequestParam @NotNull Integer id) {
        return ResponseEntity.ok(clincOwnerService.getDoctorById(id));
    }

    @GetMapping("/getNurseById")
    public ResponseEntity<?> getNurseById(@RequestParam @NotNull Integer id) {
        return ResponseEntity.ok(clincOwnerService.getNurseById(id));
    }

    @GetMapping("/getClerkById")
    public ResponseEntity<?> getClerkById(@RequestParam @NotNull Integer id) {
        return ResponseEntity.ok(clincOwnerService.getClerkById(id));
    }

    @Validated(OnCreate.class)
    @PostMapping("/registerDoctor")
    public ResponseEntity<Boolean> registerDoctor(
            @RequestBody @NotEmpty(message = "Doctor Registration List cannot be empty") List<@Valid RegisterDoctorRequest> registerDoctorReq) {
        return ResponseEntity.ok(clincOwnerService.registerDoctor(registerDoctorReq));
    }

    @Validated(OnCreate.class)
    @PostMapping("/registerNurse")
    public ResponseEntity<Boolean> registerNurse(
            @RequestBody @NotEmpty(message = "Nurse Registration List cannot be empty") List<@Valid RegisterNurseRequest> registerNurseReq) {
        return ResponseEntity.ok(clincOwnerService.registerNurse(registerNurseReq));
    }

    @Validated(OnCreate.class)
    @PostMapping("/registerClerk")
    public ResponseEntity<Boolean> registerClerk(
            @RequestBody @NotEmpty(message = "Clerk Registration List cannot be empty") List<@Valid RegisterFrontDeskRequest> registerFrontDeskReq) {
        return ResponseEntity.ok(clincOwnerService.registerFrontDesk(registerFrontDeskReq));
    }

    @Validated(OnStaffUpdate.class)
    @PutMapping("/updateDoctor")
    public ResponseEntity<Boolean> updateDoctor(
            @RequestBody @Valid RegisterDoctorRequest registerDoctorReq) {
        return ResponseEntity.ok(clincOwnerService.updateDoctor(registerDoctorReq));
    }

    @Validated(OnStaffUpdate.class)
    @PutMapping("/updateNurse")
    public ResponseEntity<Boolean> updateNurse(
            @RequestBody @Valid RegisterNurseRequest registerNurseReq) {
        return ResponseEntity.ok(clincOwnerService.updateNurse(registerNurseReq));
    }

    @Validated(OnStaffUpdate.class)
    @PutMapping("/updateClerk")
    public ResponseEntity<Boolean> updateClerk(
            @RequestBody @Valid RegisterFrontDeskRequest registerFrontDeskReq) {
        return ResponseEntity.ok(clincOwnerService.updateClerk(registerFrontDeskReq));
    }

    @PutMapping("/suspendDoctor")
    public ResponseEntity<Boolean> suspendDoctor(
            @RequestParam @NotNull Integer id) {
        return ResponseEntity.ok(clincOwnerService.suspendDoctor(id));
    }

    @PutMapping("/suspendNurse")
    public ResponseEntity<Boolean> suspendNurse(
            @RequestParam @NotNull Integer id) {
        return ResponseEntity.ok(clincOwnerService.suspendNurse(id));
    }

    @PutMapping("/suspendClerk")
    public ResponseEntity<Boolean> suspendClerk(
            @RequestParam @NotNull Integer id) {
        return ResponseEntity.ok(clincOwnerService.suspendClerk(id));
    }

    @PutMapping("/activateDoctor")
    public ResponseEntity<Boolean> activateDoctor(
            @RequestParam @NotNull Integer id) {
        return ResponseEntity.ok(clincOwnerService.activateDoctor(id));
    }

    @PutMapping("/activateNurse")
    public ResponseEntity<Boolean> activateNurse(
            @RequestParam @NotNull Integer id) {
        return ResponseEntity.ok(clincOwnerService.activateNurse(id));
    }

    @PutMapping("/activateClerk")
    public ResponseEntity<Boolean> activateClerk(
            @RequestParam @NotNull Integer id) {
        return ResponseEntity.ok(clincOwnerService.activateClerk(id));
    }

    @PostMapping("/generateClinicAppointmentSlots")
    public ResponseEntity<Boolean> generateClinicAppointmentSlots(
            @NotEmpty(message = "Date List cannot be empty") @RequestBody List<@NotNull LocalDate> generateClinicAppointmentReq) {
        return ResponseEntity.ok(clincOwnerService.generateClinicAppointmentSlots(generateClinicAppointmentReq));
    }

    @PostMapping("/generateAppointmentSlots")
    public ResponseEntity<Boolean> generateDoctorAppointmentSlots(
            @Valid @RequestBody GenerateAppointmentRequest generateAppointmentReq) {
        return ResponseEntity.ok(clincOwnerService.generateDoctorAppointmentSlots(generateAppointmentReq));
    }

    @GetMapping("/getByClinicFeedbackId")
    public ResponseEntity<List<PatientFeedbackClinic>> getByClinicFeedbackId(@RequestParam Integer clinicFeedbackId) {
        return ResponseEntity.ok(clincOwnerService.getByClinicFeedbackId(clinicFeedbackId));
    }
}
