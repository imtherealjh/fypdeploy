package com.uow.FYP_23_S1_11.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uow.FYP_23_S1_11.Constants;
import com.uow.FYP_23_S1_11.domain.Appointment;
import com.uow.FYP_23_S1_11.domain.Clinic;
import com.uow.FYP_23_S1_11.domain.Doctor;
import com.uow.FYP_23_S1_11.domain.DoctorSchedule;
import com.uow.FYP_23_S1_11.domain.UserAccount;
import com.uow.FYP_23_S1_11.domain.request.GenerateAppointmentRequest;
import com.uow.FYP_23_S1_11.domain.request.GenerateClinicAppointmentRequest;
import com.uow.FYP_23_S1_11.domain.request.QueueRequest;
import com.uow.FYP_23_S1_11.enums.EAppointmentStatus;
import com.uow.FYP_23_S1_11.enums.EWeekdays;
import com.uow.FYP_23_S1_11.repository.AppointmentRepository;
import com.uow.FYP_23_S1_11.repository.DoctorRepository;
import com.uow.FYP_23_S1_11.repository.DoctorScheduleRepository;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uow.FYP_23_S1_11.domain.EducationalMaterial;
import com.uow.FYP_23_S1_11.domain.Queue;
import com.uow.FYP_23_S1_11.domain.request.EducationalMaterialRequest;
import com.uow.FYP_23_S1_11.repository.EduMaterialRepository;
import com.uow.FYP_23_S1_11.repository.QueueRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ClerkServiceImpl implements ClerkService {
    @Autowired
    private AppointmentRepository apptRepo;
    @Autowired
    private DoctorRepository doctorRepo;
    @Autowired
    private DoctorScheduleRepository doctorScheduleRepo;
    @Autowired
    private QueueRepository queueRepo;

    //
    @Autowired
    private EduMaterialRepository eduMaterialRepo;//

    private List<Appointment> generateTimeSlots(Doctor doctor, LocalDate date) {
        DayOfWeek dow = date.getDayOfWeek();
        String output = dow.getDisplayName(TextStyle.FULL, Locale.US);
        EWeekdays day = EWeekdays.valueOf(output.toUpperCase());

        DoctorSchedule doctorSchedule = doctorScheduleRepo.findByDoctorAndDay(doctor, day);
        if (doctorSchedule == null) {
            return null;
        }

        Clinic clinic = doctor.getDoctorClinic();
        LocalTime duration = clinic.getApptDuration();

        LocalTime newTime = doctorSchedule.getStartTime();
        List<Appointment> timeslots = new ArrayList<>();
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

        return timeslots;
    }

    @Override
    public Boolean generateClinicAppointmentSlots(GenerateClinicAppointmentRequest generateClinicApptReq) {
        try {
            // get front desk's clinic object...
            UserAccount userAccount = Constants.getAuthenticatedUser();
            Clinic clinic = userAccount.getClinic();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
            LocalDate parseDate = LocalDate.parse(generateClinicApptReq.getGeneratedDate(), formatter);

            List<Doctor> doctors = doctorRepo.findByDoctorClinic(clinic);
            List<Appointment> appointmentList = new ArrayList<Appointment>();
            for (Doctor doctor : doctors) {
                List<Appointment> newAppointmentSlots = generateTimeSlots(doctor, parseDate);
                if (newAppointmentSlots != null) {
                    appointmentList.addAll(newAppointmentSlots);
                }
            }

            apptRepo.saveAll(appointmentList);
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    @Override
    public Boolean generateDoctorAppointmentSlots(GenerateAppointmentRequest generateDoctorApptReq) {
        try {
            // TODO: still need to validate is it the same clinic
            Optional<Doctor> doctor = doctorRepo.findById(generateDoctorApptReq.getDoctorId());
            if (doctor.isEmpty() || !(doctor.get().getDoctorSchedule().size() >= 1)) {
                throw new IllegalArgumentException("No doctor found!!");
            }

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
            LocalDate parseDate = LocalDate.parse(generateDoctorApptReq.getGeneratedDate(), formatter);

            List<Appointment> generatedSlots = generateTimeSlots(doctor.get(), parseDate);
            if (generatedSlots != null) {
                apptRepo.saveAll(generatedSlots);
            }

            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    //
    @Override
    public Boolean createEduMaterial(EducationalMaterialRequest request) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        try {
            EducationalMaterial educationalMaterial = (EducationalMaterial) mapper.convertValue(request,
                    EducationalMaterial.class);
            eduMaterialRepo.save(educationalMaterial);

            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    @Override
    public Boolean deleteEduMaterial(Integer materialId) {
        try {
            UserAccount currentUser = Constants.getAuthenticatedUser();
            Optional<EducationalMaterial> materialOptional = eduMaterialRepo.findById(materialId);
            if (materialOptional.isEmpty()) {
                throw new IllegalArgumentException("Educational Material does not exist...");
            }
            EducationalMaterial eduMat = materialOptional.get();
            eduMaterialRepo.delete(eduMat);
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    @Override
    public Boolean updateEduMaterial(Integer materialId, EducationalMaterialRequest request) {
        try {
            UserAccount currentUser = Constants.getAuthenticatedUser();
            Optional<EducationalMaterial> materialOptional = eduMaterialRepo.findById(materialId);
            if (materialOptional.isEmpty()) {
                throw new IllegalArgumentException("Educational Material does not exist...");
            }
            EducationalMaterial eduMat = materialOptional.get();
            eduMat.setTitle(request.getTitle());
            eduMat.setContent(request.getContent());
            eduMaterialRepo.save(eduMat);
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    @Override
    public Boolean updateQueueNumber(Integer queueId,
            QueueRequest updateQueueRequest) {
        Optional<Queue> originalQueue = queueRepo
                .findById(queueId);

        if (originalQueue.isEmpty() == false) {
            Queue queue = originalQueue.get();
            queue.setQueueNumber(updateQueueRequest.getQueueNumber());
            queue.setStatus(updateQueueRequest.getStatus());
            queueRepo.save(queue);
            return true;
        } else {
            throw new IllegalArgumentException("Queue not found...");
        }

    }
}
