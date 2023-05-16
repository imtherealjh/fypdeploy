package com.uow.FYP_23_S1_11.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uow.FYP_23_S1_11.Constants;
import com.uow.FYP_23_S1_11.domain.Clinic;
import com.uow.FYP_23_S1_11.domain.EducationalMaterial;
import com.uow.FYP_23_S1_11.domain.UserAccount;
import com.uow.FYP_23_S1_11.domain.response.EduMaterialResponse;
import com.uow.FYP_23_S1_11.repository.EduMaterialRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private EduMaterialRepository eduMaterialRepo;

    @Override
    public Map<?, ?> getAllEduMaterial(Pageable pageable) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        Page<?> eduMaterial = eduMaterialRepo.findAll(pageable).map(page -> {
            EduMaterialResponse res = (EduMaterialResponse) mapper.convertValue(page, EduMaterialResponse.class);
            res.setClinicName(page.getClinic().getClinicName());
            return res;
        });
        return Constants.convertToResponse(eduMaterial);
    }

    @Override
    public Map<?, ?> getEduMaterialById(Integer materialId) {
        try {
            UserAccount user = Constants.getAuthenticatedUser();
            Clinic clinic = Optional
                    .ofNullable(user.getFrontDesk())
                    .map(elem -> elem.getFrontDeskClinic())
                    .orElse(null);
            Optional<EducationalMaterial> materialOptional = eduMaterialRepo.findById(materialId);
            if (materialOptional.isEmpty()) {
                throw new IllegalArgumentException("Educational material does not exist..");
            }

            EducationalMaterial material = materialOptional.get();
            Map<String, Object> response = new HashMap<>();
            response.put("content", material);
            response.put("editable", material.getClinic().getClinicId() == clinic.getClinicId());

            return response;
        } catch (Exception e) {
            System.out.println(e);
            return new HashMap<>();
        }
    }
}
