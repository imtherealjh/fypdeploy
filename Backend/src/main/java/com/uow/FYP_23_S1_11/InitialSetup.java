package com.uow.FYP_23_S1_11;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.uow.FYP_23_S1_11.domain.Specialty;
import com.uow.FYP_23_S1_11.domain.UserAccount;
import com.uow.FYP_23_S1_11.enums.ERole;
import com.uow.FYP_23_S1_11.repository.SpecialtyRepository;
import com.uow.FYP_23_S1_11.repository.UserAccountRepository;
import com.uow.FYP_23_S1_11.service.UserAccountService;

@Component
public class InitialSetup {
    @Autowired
    private SpecialtyRepository specialtyRepo;
    @Autowired
    private UserAccountRepository userAccRepo;
    @Autowired
    private UserAccountService userAccService;
    @Value("${spring.mail.username}")
    private String sender;

    @EventListener
    public void onApplicationEvent(ApplicationReadyEvent event) throws Exception {
        List<Specialty> specialtyList = new ArrayList<Specialty>();
        specialtyList.add(Specialty.builder().type("Anaesthesiology").build());
        specialtyList.add(Specialty.builder().type("Dermatology").build());
        specialtyList.add(Specialty.builder().type("Emergency Medicine").build());
        specialtyList.add(Specialty.builder().type("Pain Medicine").build());
        specialtyList.add(Specialty.builder().type("Psychiatry").build());
        specialtyList.add(Specialty.builder().type("Urology").build());

        List<Specialty> alreadyExist = specialtyRepo
                .findByTypeIn(specialtyList.stream().map(x -> x.getType()).collect(Collectors.toList()));
        Set<String> existType = alreadyExist.stream().map(Specialty::getType).collect(Collectors.toSet());
        specialtyList = specialtyList.stream().filter(e -> !existType.contains(e.getType()))
                .collect(Collectors.toList());
        specialtyRepo.saveAll(specialtyList);

        Optional<UserAccount> sysAdmin = userAccRepo.findByUsername("admin");
        if (sysAdmin.isEmpty()) {
            UserAccount account = new UserAccount();
            account.setUsername("admin");
            account.setPassword("admin");
            account.setIsEnabled(true);
            userAccService.registerAccount(account, sender, ERole.SYSTEM_ADMIN);
        }

    }
}
