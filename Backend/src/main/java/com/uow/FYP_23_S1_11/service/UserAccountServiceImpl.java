package com.uow.FYP_23_S1_11.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.uow.FYP_23_S1_11.domain.Clinic;
import com.uow.FYP_23_S1_11.domain.Patient;
import com.uow.FYP_23_S1_11.domain.UserAccount;
import com.uow.FYP_23_S1_11.domain.request.AccessTokenRequest;
import com.uow.FYP_23_S1_11.domain.request.ClinicRegisterRequest;
import com.uow.FYP_23_S1_11.domain.request.LoginRequest;
import com.uow.FYP_23_S1_11.domain.request.PatientRegisterRequest;
import com.uow.FYP_23_S1_11.domain.response.AuthResponse;
import com.uow.FYP_23_S1_11.enums.ETokenType;
import com.uow.FYP_23_S1_11.enums.EUserRole;
import com.uow.FYP_23_S1_11.repository.ClinicRepository;
import com.uow.FYP_23_S1_11.repository.PatientRepository;
import com.uow.FYP_23_S1_11.repository.UserAccountRepository;
import com.uow.FYP_23_S1_11.utils.JwtUtils;

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
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public AuthResponse authenticate(LoginRequest loginRequest) {
        // using built-in authentication manager to validate the login request
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()));
        var user = userAccRepo.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found!!"));
        String refreshToken = jwtUtils.generateToken(ETokenType.REFRESH_TOKEN, user);
        String accessToken = jwtUtils.generateToken(ETokenType.ACCESS_TOKEN, user);
        return AuthResponse
                .builder()
                .refreshToken(refreshToken)
                .accessToken(accessToken)
                .build();
    }

    @Override
    public AuthResponse regenerateAccessToken(AccessTokenRequest accessTokenReq) {
        try {
            String refreshToken = accessTokenReq.getRefreshToken();
            ETokenType type = ETokenType.REFRESH_TOKEN;
            String username = jwtUtils.extractUserFromToken(type, refreshToken);
            if (username != null) {
                UserDetails userDetails = userAccRepo.findByUsername(username)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found!!"));
                if (jwtUtils.isTokenValid(type, refreshToken, userDetails)) {
                    String newAccessToken = jwtUtils.generateToken(type, userDetails);
                    return AuthResponse
                            .builder()
                            .refreshToken(null)
                            .accessToken(newAccessToken)
                            .build();
                }
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public UserAccount registerAccount(UserAccount account, EUserRole userRole) {
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
            UserAccount registeredAccount = registerAccount(newAccount, EUserRole.CLINIC_OWNER);

            Clinic newClinic = (Clinic) mapper.convertValue(clinicReq, Clinic.class);
            newClinic.setClinicAccount(registeredAccount);
            clincRepo.save(newClinic);

            return true;
        } catch (IllegalArgumentException e) {
            return false;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    @Override
    public Boolean registerPatientAccount(PatientRegisterRequest patientReq) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        try {
            UserAccount newAccount = (UserAccount) mapper.convertValue(patientReq, UserAccount.class);
            UserAccount registeredAccount = registerAccount(newAccount, EUserRole.PATIENT);

            Patient newPatient = (Patient) mapper.convertValue(patientReq, Patient.class);
            newPatient.setPatientAccount(registeredAccount);
            patientRepo.save(newPatient);

            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
