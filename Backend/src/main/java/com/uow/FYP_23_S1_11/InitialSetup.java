package com.uow.FYP_23_S1_11;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.uow.FYP_23_S1_11.domain.Specialty;
import com.uow.FYP_23_S1_11.repository.SpecialtyRepository;

@Component
public class InitialSetup {
    @Autowired private SpecialtyRepository specialtyRepo;

    @EventListener
    public void onApplicationEvent(ApplicationReadyEvent event) throws Exception {
        List<Specialty> specialtyList = new ArrayList<Specialty>();
        specialtyList.add(Specialty.builder().type("Anaesthesiology").build());
        specialtyList.add(Specialty.builder().type("Dermatology").build());
        specialtyList.add(Specialty.builder().type("Emergency Medicine").build());
        specialtyList.add(Specialty.builder().type("Pain Medicine").build());
        specialtyList.add(Specialty.builder().type("Psychiatry").build());
        specialtyList.add(Specialty.builder().type("Urology").build());

        List<Specialty> alreadyExist = specialtyRepo.findByTypeIn(specialtyList.stream().map(x -> x.getType()).collect(Collectors.toList()));
        specialtyList = specialtyList.stream().filter(e -> alreadyExist.contains(e)).collect(Collectors.toList());
        specialtyRepo.saveAll(specialtyList);
    }
}
