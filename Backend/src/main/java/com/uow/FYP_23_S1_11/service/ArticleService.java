package com.uow.FYP_23_S1_11.service;

import java.util.Map;

import org.springframework.data.domain.Pageable;

public interface ArticleService {
    public Map<?, ?> getAllEduMaterial(Pageable pageable);

    public Map<?, ?> getEduMaterialById(Integer materialId);
}
