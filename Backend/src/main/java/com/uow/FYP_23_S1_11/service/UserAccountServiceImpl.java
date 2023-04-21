package com.uow.FYP_23_S1_11.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
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
import com.uow.FYP_23_S1_11.Constants;
import com.uow.FYP_23_S1_11.domain.Clinic;
import com.uow.FYP_23_S1_11.domain.Patient;
import com.uow.FYP_23_S1_11.domain.UserAccount;
import com.uow.FYP_23_S1_11.domain.request.ClinicRegisterRequest;
import com.uow.FYP_23_S1_11.domain.request.LoginRequest;
import com.uow.FYP_23_S1_11.domain.request.PatientRegisterRequest;
import com.uow.FYP_23_S1_11.domain.response.AuthResponse;
import com.uow.FYP_23_S1_11.enums.ETokenType;
import com.uow.FYP_23_S1_11.enums.ERole;
import com.uow.FYP_23_S1_11.repository.ClinicRepository;
import com.uow.FYP_23_S1_11.repository.PatientRepository;
import com.uow.FYP_23_S1_11.repository.UserAccountRepository;
import com.uow.FYP_23_S1_11.utils.JwtUtils;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
@Transactional
public class UserAccountServiceImpl implements UserAccountService {
    @Autowired
    private UserAccountRepository userAccRepo;
    @Autowired
    private ClinicRepository clincRepo;
    @Autowired
    private PatientRepository patientRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JavaMailSender mailSender;

    @Value("${refresh.jwtexpirationms}")
    private int refreshTokenExpiry;

    @Value("${spring.mail.username}")
    private String sender;

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

            String newAccessToken = jwtUtils.generateToken(ETokenType.ACCESS_TOKEN, user);

            AuthResponse auth = AuthResponse
                    .builder()
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

        String refreshToken = jwtUtils.generateToken(ETokenType.REFRESH_TOKEN, user);
        String accessToken = jwtUtils.generateToken(ETokenType.ACCESS_TOKEN, user);

        Cookie cookie = new Cookie("refreshToken", refreshToken);
        cookie.setAttribute("SameSite", "None");
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(refreshTokenExpiry / 1000);

        response.addCookie(cookie);

        AuthResponse auth = AuthResponse
                .builder()
                .role(user.getRole().name())
                .accessToken(accessToken)
                .build();

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), auth);
    }

    @Override
    public UserAccount registerAccount(UserAccount account, ERole userRole) {
        Optional<UserAccount> user = userAccRepo.findByUsername(account.getUsername());
        if (user.isPresent()) {
            throw new IllegalArgumentException("Username has already been registered...");
        }

        account.setPassword(passwordEncoder.encode(account.getPassword()));
        account.setRole(userRole);

        return userAccRepo.save(account);
    }

    @Override
    public Boolean registerClinicAccount(ClinicRegisterRequest clinicReq) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.registerModule(new JavaTimeModule());
        try {
            UserAccount newAccount = (UserAccount) mapper.convertValue(clinicReq, UserAccount.class);
            UserAccount registeredAccount = registerAccount(newAccount, ERole.CLINIC_OWNER);

            Clinic newClinic = (Clinic) mapper.convertValue(clinicReq, Clinic.class);
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

    @Override
    public Boolean registerPatientAccount(PatientRegisterRequest patientReq, HttpServletRequest request) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        try {
            UserAccount newAccount = (UserAccount) mapper.convertValue(patientReq, UserAccount.class);
            UserAccount registeredAccount = registerAccount(newAccount, ERole.PATIENT);

            Patient newPatient = (Patient) mapper.convertValue(patientReq, Patient.class);
            newPatient.setPatientAccount(registeredAccount);
            patientRepo.save(newPatient);

            return true;
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, String token) {
        try {
            if (token == null) {

            }
        } catch (Exception e) {

        }
    }

    public String createVerificationCode() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        return generatedString;
    }

    public void sendEmail(UserAccount userAccount, HttpServletRequest request)
            throws MessagingException, UnsupportedEncodingException {
        String baseURL = Constants.makeUrl(request);

        String toAddress = userAccount.getEmail();
        String fromAddress = sender;
        String senderName = "GoDoctor";
        String subject = "Please verify your registration";
        String content = "Dear [[name]],<br>"
                + "Please click the link below to verify your registration:<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
                + "Thank you,<br>"
                + "Your company name.";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);

        content = content.replace("[[name]]", userAccount.getUsername());
        String verifyURL = baseURL + "/verify?code=" + userAccount.getVerificationCode();

        content = content.replace("[[URL]]", verifyURL);

        helper.setText(content, true);

        mailSender.send(message);

    }

}