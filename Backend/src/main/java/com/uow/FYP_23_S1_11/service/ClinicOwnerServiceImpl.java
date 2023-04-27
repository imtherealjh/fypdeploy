package com.uow.FYP_23_S1_11.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uow.FYP_23_S1_11.Constants;
import com.uow.FYP_23_S1_11.domain.Appointment;
import com.uow.FYP_23_S1_11.domain.Clinic;
import com.uow.FYP_23_S1_11.domain.Doctor;
import com.uow.FYP_23_S1_11.domain.DoctorSchedule;
import com.uow.FYP_23_S1_11.domain.FrontDesk;
import com.uow.FYP_23_S1_11.domain.Nurse;
import com.uow.FYP_23_S1_11.domain.PatientFeedbackClinic;
import com.uow.FYP_23_S1_11.domain.Specialty;
import com.uow.FYP_23_S1_11.domain.UserAccount;
import com.uow.FYP_23_S1_11.domain.request.DoctorScheduleRequest;
import com.uow.FYP_23_S1_11.domain.request.GenerateAppointmentRequest;
import com.uow.FYP_23_S1_11.domain.request.RegisterDoctorRequest;
import com.uow.FYP_23_S1_11.domain.request.RegisterFrontDeskRequest;
import com.uow.FYP_23_S1_11.domain.request.RegisterNurseRequest;
import com.uow.FYP_23_S1_11.domain.response.PatientAppointmentDetails;
import com.uow.FYP_23_S1_11.domain.response.RetrieveDoctorPatient;
import com.uow.FYP_23_S1_11.domain.response.StaffAccountDetails;
import com.uow.FYP_23_S1_11.enums.EAppointmentStatus;
import com.uow.FYP_23_S1_11.enums.ERole;
import com.uow.FYP_23_S1_11.enums.EWeekdays;
import com.uow.FYP_23_S1_11.repository.AppointmentRepository;
import com.uow.FYP_23_S1_11.repository.DoctorRepository;
import com.uow.FYP_23_S1_11.repository.DoctorScheduleRepository;
import com.uow.FYP_23_S1_11.repository.FrontDeskRepository;
import com.uow.FYP_23_S1_11.repository.NurseRepository;
import com.uow.FYP_23_S1_11.repository.PatientFeedbackClinicRepository;
import com.uow.FYP_23_S1_11.repository.SpecialtyRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class ClinicOwnerServiceImpl implements ClinicOwnerService {
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private UserAccountService userAccountService;
    @Autowired
    private DoctorRepository doctorRepo;
    @Autowired
    private DoctorScheduleRepository doctorScheduleRepo;
    @Autowired
    private AppointmentRepository apptRepo;
    @Autowired
    private NurseRepository nurseRepo;
    @Autowired
    private FrontDeskRepository frontDeskRepo;
    @Autowired
    private SpecialtyRepository specialtyRepo;
    @Autowired
    private PatientFeedbackClinicRepository patientFeedbackClinicRepo;

    @Override
    public Object getVisitingPaitents(LocalDate date) {
        UserAccount user = Constants.getAuthenticatedUser();
        Clinic clinic = user.getClinic();

        TypedQuery<PatientAppointmentDetails> query = entityManager.createQuery(
                "SELECT "
                        + "new com.uow.FYP_23_S1_11.domain.response.PatientAppointmentDetails(a.apptPatient, a.apptTime) "
                        + "FROM Appointment a "
                        + "LEFT JOIN a.apptDoctor d "
                        + "WHERE a.apptPatient IS NOT NULL AND "
                        + "d.doctorClinic = :clinic AND "
                        + "a.status = :status AND "
                        + "a.apptDate = :date "
                        + "GROUP BY a.apptPatient, a.apptTime",
                PatientAppointmentDetails.class);
        query.setParameter("clinic", clinic);
        query.setParameter("date", date);
        query.setParameter("status", EAppointmentStatus.BOOKED);

        RetrieveDoctorPatient object = new RetrieveDoctorPatient(query.getResultList(),
                query.getResultList().size());
        return object;
    }

    @Override
    public List<?> getAllStaffs() {
        UserAccount account = Constants.getAuthenticatedUser();

        TypedQuery<StaffAccountDetails> query = entityManager.createQuery(
                "SELECT new com.uow.FYP_23_S1_11.domain.response.StaffAccountDetails(d.doctorId, d.name, ua.role, d.email) FROM UserAccount ua "
                        + "JOIN ua.doctor d WHERE d.doctorClinic = :clinic UNION "
                        + "SELECT new com.uow.FYP_23_S1_11.domain.response.StaffAccountDetails(n.nurseId,n.name, ua.role, n.email) FROM UserAccount ua "
                        + "JOIN ua.nurse n WHERE n.nurseClinic = :clinic UNION "
                        + "SELECT new com.uow.FYP_23_S1_11.domain.response.StaffAccountDetails(fd.frontDeskId, fd.name, ua.role, fd.email) FROM UserAccount ua "
                        + "JOIN ua.frontDesk fd WHERE fd.frontDeskClinic = :clinic",
                StaffAccountDetails.class);
        query.setParameter("clinic", account.getClinic());
        return query.getResultList();
    }

    @Override
    public List<?> getAllDoctors() {
        UserAccount account = Constants.getAuthenticatedUser();
        return doctorRepo.findByDoctorClinic(account.getClinic());
    }

    private List<Appointment> generateTimeSlots(LocalDate date, Doctor doctor) {
        DayOfWeek dow = date.getDayOfWeek();
        String output = dow.getDisplayName(TextStyle.FULL, Locale.US);
        EWeekdays day = EWeekdays.valueOf(output.toUpperCase());

        List<Appointment> appointments = apptRepo.findByApptDateAndApptDoctor(date, doctor);
        DoctorSchedule doctorSchedule = doctorScheduleRepo.findByDoctorAndDay(doctor, day);
        // check if user have a schedule or have already generated a schedule
        if (appointments.size() > 0 || doctorSchedule == null) {
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
    public Boolean generateClinicAppointmentSlots(List<LocalDate> generateClinicAppointmentReq) {
        try {
            // get clinic object...
            UserAccount userAccount = Constants.getAuthenticatedUser();
            Clinic clinic = userAccount.getClinic();

            List<Appointment> appointmentList = new ArrayList<Appointment>();
            for (LocalDate date : generateClinicAppointmentReq) {
                if (date.isBefore(LocalDate.now())) {
                    continue;
                }

                List<Doctor> doctors = doctorRepo.findByDoctorClinic(clinic);
                for (Doctor doctor : doctors) {
                    List<Appointment> generatedSlots = generateTimeSlots(date, doctor);
                    if (generatedSlots == null) {
                        continue;
                    }
                    appointmentList.addAll(generatedSlots);
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
            UserAccount userAccount = Constants.getAuthenticatedUser();
            Clinic clinic = userAccount.getClinic();

            List<LocalDate> dates = generateDoctorApptReq.getApptDates();
            List<Doctor> doctors = generateDoctorApptReq.getDoctorIds().stream()
                    .map(id -> doctorRepo.findByDoctorIdAndDoctorClinic(id, clinic)).filter(Objects::nonNull)
                    .collect(Collectors.toList());

            List<Appointment> appointmentList = new ArrayList<Appointment>();
            for (LocalDate date : dates) {
                if (date.isBefore(LocalDate.now())) {
                    continue;
                }

                // validate is it the same clinic
                for (Doctor doctor : doctors) {
                    List<Appointment> generatedSlots = generateTimeSlots(date, doctor);
                    if (generatedSlots == null) {
                        continue;
                    }
                    appointmentList.addAll(generatedSlots);
                }
            }

            apptRepo.saveAll(appointmentList);
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    private boolean conflictSchedule(DoctorScheduleRequest r1, DoctorScheduleRequest r2) {
        if (!r1.getDay().equals(r2.getDay())) {
            return false;
        }

        var aStart = LocalTime.parse(r1.getStartTime());
        var aEnd = LocalTime.parse(r1.getEndTime());
        var bStart = LocalTime.parse(r2.getStartTime());
        var bEnd = LocalTime.parse(r2.getEndTime());
        return !(aStart.compareTo(bEnd) <= 0 && aEnd.compareTo(bStart) <= 0);
    }

    @Override
    public Boolean registerDoctor(
            List<RegisterDoctorRequest> registerDoctorRequest) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        try {
            // get owner's clinic object...
            UserAccount userAccount = Constants.getAuthenticatedUser();
            Clinic clinic = userAccount.getClinic();

            List<Doctor> newDoctorList = new ArrayList<Doctor>();
            for (RegisterDoctorRequest object : registerDoctorRequest) {
                UserAccount newAccount = (UserAccount) mapper.convertValue(object, UserAccount.class);
                UserAccount registeredAccount = userAccountService.registerAccount(newAccount, object.getEmail(),
                        ERole.DOCTOR);
                List<Specialty> specialty = specialtyRepo.findByTypeIn(object.getSpecialty());

                Doctor newDoctor = (Doctor) mapper.convertValue(object, Doctor.class);

                newDoctor.setDoctorAccount(registeredAccount);
                newDoctor.setDoctorClinic(clinic);
                newDoctor.setDoctorSpecialty(specialty);

                newDoctorList.add(doctorRepo.save(newDoctor));

                TreeSet<DoctorScheduleRequest> ts = object.getSchedule();
                List<DoctorScheduleRequest> dsl = new ArrayList<DoctorScheduleRequest>(ts);
                if (dsl != null && dsl.size() > 0) {
                    List<DoctorSchedule> _schedules = new ArrayList<DoctorSchedule>();
                    for (int i = 0; i < dsl.size(); i++) {
                        var elem1 = dsl.get(i);

                        if (i != dsl.size() - 1) {
                            var elem2 = dsl.get(i + 1);
                            if (conflictSchedule(elem1, elem2)) {
                                throw new IllegalArgumentException("Conflicting schedules");
                            }
                        } else if (LocalTime.parse(elem1.getStartTime()).isBefore(clinic.getOpeningHrs())) {
                            throw new IllegalArgumentException(
                                    "Doctor's work start time should not be before the opening hours of the clinic...");
                        } else if (LocalTime.parse(elem1.getEndTime()).isAfter(clinic.getClosingHrs())) {
                            throw new IllegalArgumentException(
                                    "Doctor's end start time should not be after the closing hours of the clinic...");
                        }

                        DoctorSchedule newSchedule = (DoctorSchedule) mapper.convertValue(elem1,
                                DoctorSchedule.class);
                        newSchedule.setDoctor(newDoctor);
                        _schedules.add(newSchedule);
                    }

                    doctorScheduleRepo.saveAll(_schedules);
                }
            }
            return true;
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    @Override
    public Boolean registerNurse(List<RegisterNurseRequest> registerNurseReq) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        try {
            // get owner's clinic object...
            UserAccount userAccount = Constants.getAuthenticatedUser();
            Clinic clinic = userAccount.getClinic();

            List<Nurse> nurses = registerNurseReq.stream().map(obj -> {
                UserAccount newAccount = (UserAccount) mapper.convertValue(obj, UserAccount.class);
                UserAccount registeredAccount = userAccountService.registerAccount(newAccount, obj.getEmail(),
                        ERole.NURSE);

                Nurse nurse = (Nurse) mapper.convertValue(obj, Nurse.class);
                nurse.setNurseAccount(registeredAccount);
                nurse.setNurseClinic(clinic);

                return nurse;
            }).collect(Collectors.toList());

            nurseRepo.saveAll(nurses);
            return true;
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    @Override
    public Boolean registerFrontDesk(List<RegisterFrontDeskRequest> registerFrontDeskReq) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        try {
            // get owner's clinic object...
            UserAccount userAccount = Constants.getAuthenticatedUser();
            Clinic clinic = userAccount.getClinic();

            List<FrontDesk> clerks = registerFrontDeskReq.stream().map(obj -> {
                UserAccount newAccount = (UserAccount) mapper.convertValue(obj, UserAccount.class);
                UserAccount registeredAccount = userAccountService.registerAccount(newAccount, obj.getEmail(),
                        ERole.FRONT_DESK);

                FrontDesk frontDesk = (FrontDesk) mapper.convertValue(obj, FrontDesk.class);
                frontDesk.setFrontDeskAccount(registeredAccount);
                frontDesk.setFrontDeskClinic(clinic);

                return frontDesk;
            }).collect(Collectors.toList());

            frontDeskRepo.saveAll(clerks);
            return true;
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    @Override
    public List<PatientFeedbackClinic> getByClinicFeedbackId(Integer clinicFeedbackId) {
        List<PatientFeedbackClinic> patientFeedbackClinic = patientFeedbackClinicRepo
                .findByClinicFeedbackId(clinicFeedbackId);
        if (patientFeedbackClinic.isEmpty() == false) {
            return patientFeedbackClinic;
        } else {
            throw new IllegalArgumentException("Feedback not found...");
        }
    }

}
