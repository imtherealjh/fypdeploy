package com.uow.FYP_23_S1_11.domain.response;

import com.uow.FYP_23_S1_11.domain.EducationalMaterial;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EduMaterialEditResponse {
    private EducationalMaterial eduMaterial;
    private Boolean editable;
}
