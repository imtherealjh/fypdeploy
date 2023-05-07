package com.uow.FYP_23_S1_11.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.uow.FYP_23_S1_11.domain.Clinic;
import com.uow.FYP_23_S1_11.domain.Patient;
import com.uow.FYP_23_S1_11.domain.PatientMedicalRecords;
import com.uow.FYP_23_S1_11.domain.UserAccount;
import com.uow.FYP_23_S1_11.domain.request.RegisterClinicRequest;
import com.uow.FYP_23_S1_11.domain.request.LoginRequest;
import com.uow.FYP_23_S1_11.domain.request.RegisterPatientRequest;
import com.uow.FYP_23_S1_11.domain.response.AuthResponse;
import com.uow.FYP_23_S1_11.enums.ETokenType;
import com.uow.FYP_23_S1_11.enums.ERole;
import com.uow.FYP_23_S1_11.repository.ClinicRepository;
import com.uow.FYP_23_S1_11.repository.PatientMedicalRecordsRepository;
import com.uow.FYP_23_S1_11.repository.PatientRepository;
import com.uow.FYP_23_S1_11.repository.UserAccountRepository;
import com.uow.FYP_23_S1_11.utils.JwtUtils;

import jakarta.mail.MessagingException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
@Transactional
public class UserAccountServiceImpl implements UserAccountService {
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserAccountRepository userAccRepo;
    @Autowired
    private ClinicRepository clincRepo;
    @Autowired
    private PatientRepository patientRepo;
    @Autowired
    private PatientMedicalRecordsRepository patientMdRepo;

    @Value("${refresh.jwtexpirationms}")
    private int refreshTokenExpiry;

    @Value("${spring.mail.username}")
    private String sender;

    @Value("${CLIENT.URL}")
    private String frontendUrl;

    @Override
    public void refresh(HttpServletRequest request,
            HttpServletResponse response, String token) throws StreamWriteException, DatabindException, IOException {
        try {
            if (token == null || token.isEmpty()) {
                response.sendError(401, "Invalid refresh token...");
                return;
            }

            String username = jwtUtils.extractUserFromToken(ETokenType.REFRESH_TOKEN, token);
            if (username == null) {
                throw new IllegalArgumentException("No username found in token...");
            }

            UserAccount user = userAccRepo.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found!!"));
            if (!jwtUtils.isTokenValid(ETokenType.REFRESH_TOKEN, token, user)) {
                throw new IllegalArgumentException("Token is not valid...");
            }

            if (user.getRole() == ERole.DOCTOR || user.getRole() == ERole.NURSE || user.getRole() == ERole.FRONT_DESK) {
                Clinic clinic = new Clinic();
                if (user.getRole() == ERole.DOCTOR) {
                    clinic = user.getDoctor().getDoctorClinic();
                } else if (user.getRole() == ERole.NURSE) {
                    clinic = user.getNurse().getNurseClinic();
                } else if (user.getRole() == ERole.FRONT_DESK) {
                    clinic = user.getFrontDesk().getFrontDeskClinic();
                }

                TypedQuery<Boolean> query = entityManager
                        .createQuery("SELECT ua.isEnabled FROM UserAccount ua WHERE ua.clinic = :clinic",
                                Boolean.class);
                query.setParameter("clinic", clinic);
                if (!query.getSingleResult()) {
                    throw new IllegalArgumentException("Unable to login...");
                }
            }

            String newAccessToken = jwtUtils.generateToken(ETokenType.ACCESS_TOKEN, user);
            String name = "admin";
            if (user.getRole() != ERole.SYSTEM_ADMIN) {
                TypedQuery<String> query = entityManager.createNamedQuery("findNameInTables", String.class);
                query.setParameter("account", user);
                name = query.getSingleResult();
            }

            AuthResponse auth = AuthResponse
                    .builder()
                    .name(name)
                    .role(user.getRole().name())
                    .accessToken(newAccessToken)
                    .build();

            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            new ObjectMapper().writeValue(response.getOutputStream(), auth);
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            System.out.println(e);
            response.sendError(500, "Error occured while refreshing token");
        }

    }

    @Override
    public void authenticate(LoginRequest loginRequest, HttpServletRequest request,
            HttpServletResponse response, String token) throws StreamWriteException, DatabindException, IOException {

        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        // using built-in authentication manager to validate the login request
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password));

        var user = userAccRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found!!"));

        if (user.getRole() == ERole.DOCTOR || user.getRole() == ERole.NURSE || user.getRole() == ERole.FRONT_DESK) {
            Clinic clinic = new Clinic();
            if (user.getRole() == ERole.DOCTOR) {
                clinic = user.getDoctor().getDoctorClinic();
            } else if (user.getRole() == ERole.NURSE) {
                clinic = user.getNurse().getNurseClinic();
            } else if (user.getRole() == ERole.FRONT_DESK) {
                clinic = user.getFrontDesk().getFrontDeskClinic();
            }

            TypedQuery<Boolean> query = entityManager
                    .createQuery("SELECT ua.isEnabled FROM UserAccount ua WHERE ua.clinic = :clinic", Boolean.class);
            query.setParameter("clinic", clinic);
            if (!query.getSingleResult()) {
                throw new IllegalArgumentException("Unable to login...");
            }
        }

        String refreshToken = jwtUtils.generateToken(ETokenType.REFRESH_TOKEN, user);
        String accessToken = jwtUtils.generateToken(ETokenType.ACCESS_TOKEN, user);

        String name = "admin";
        if (user.getRole() != ERole.SYSTEM_ADMIN) {
            TypedQuery<String> query = entityManager.createNamedQuery("findNameInTables", String.class);
            query.setParameter("account", user);
            name = query.getSingleResult();
        }

        Cookie cookie = new Cookie("refreshToken",
                refreshToken);
        cookie.setAttribute("SameSite", "None");
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(refreshTokenExpiry / 1000);

        response.addCookie(cookie);

        AuthResponse auth = AuthResponse
                .builder()
                .name(name)
                .role(user.getRole().name())
                .accessToken(accessToken)
                .build();

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), auth);
    }

    @Override
    public UserAccount registerAccount(UserAccount account, String email, ERole userRole) {
        Optional<UserAccount> user = userAccRepo.findByUsername(account.getUsername());

        TypedQuery<String> query = entityManager.createNamedQuery("findEmailInTables", String.class);
        query.setParameter("email", email);

        List<String> results = query.getResultList();
        if (user.isPresent() || !results.isEmpty()) {
            throw new IllegalArgumentException("Username/Email has already been registered...");
        }

        account.setPassword(passwordEncoder.encode(account.getPassword()));
        account.setRole(userRole);

        return userAccRepo.save(account);
    }

    @Override
    public Boolean registerClinicAccount(RegisterClinicRequest clinicReq) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.registerModule(new JavaTimeModule());
        try {
            UserAccount newAccount = (UserAccount) mapper.convertValue(clinicReq, UserAccount.class);
            UserAccount registeredAccount = registerAccount(newAccount, clinicReq.getEmail(), ERole.CLINIC_OWNER);

            Clinic newClinic = (Clinic) mapper.convertValue(clinicReq, Clinic.class);
            newClinic.setLicenseProof(clinicReq.getCustomLicenseProof().getBytes());
            newClinic.setClinicAccount(registeredAccount);
            clincRepo.save(newClinic);

            return true;
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    private String generateVerificationCode() {
        String verificationCode;
        while (true) {
            verificationCode = UUID.randomUUID().toString();
            UserAccount user = userAccRepo.findByVerificationCode(verificationCode);
            if (user == null) {
                return verificationCode;
            }
        }
    }

    private void sendVerificationEmail(UserAccount user, String email)
            throws MessagingException, UnsupportedEncodingException {

        String senderName = "GoDoctor";
        String subject = "Please verify your registration";
        String content = "Dear [[name]],<br/>"
                + "Please click the link below to verify your registration:"
                + "<h3 style='margin: 0'><a href=\"[[URL]]\" target=\"_self\">[[URL]]</a></h3><br/>"
                + "Thank you,<br/>"
                + "GoDoctor.";

        String verifyURL = frontendUrl + "verify/" + user.getVerificationCode();
        content = content.replace("[[URL]]", verifyURL);
        content = content.replace("[[name]]", user.getUsername());

        emailService.sendEmail(sender, senderName, email, subject, content);
    }

    @Override
    public Boolean registerPatientAccount(RegisterPatientRequest patientReq, HttpServletRequest request) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        try {

            UserAccount newAccount = (UserAccount) mapper.convertValue(patientReq, UserAccount.class);
            newAccount.setVerificationCode(generateVerificationCode());

            UserAccount registeredAccount = registerAccount(newAccount, patientReq.getEmail(), ERole.PATIENT);
            Patient newPatient = (Patient) mapper.convertValue(patientReq, Patient.class);
            newPatient.setPatientAccount(registeredAccount);
            patientRepo.save(newPatient);

            PatientMedicalRecords patientMd = new PatientMedicalRecords();
            patientMd.setPatientmd(newPatient);
            patientMdRepo.save(patientMd);

            sendVerificationEmail(registeredAccount, patientReq.getEmail());
            return true;
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, String token) {
        Cookie cookie = new Cookie("refreshToken", null);
        cookie.setAttribute("SameSite", "None");
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0);

        response.addCookie(cookie);
    }

    @Override
    public Boolean verify(String verificationCode) {
        UserAccount user = userAccRepo.findByVerificationCode(verificationCode);
        if (user == null || user.getIsEnabled()) {
            throw new IllegalArgumentException("No verifcation code to be verified");
        }

        user.setVerificationCode(null);
        user.setIsEnabled(true);
        userAccRepo.save(user);
        return true;
    }

}