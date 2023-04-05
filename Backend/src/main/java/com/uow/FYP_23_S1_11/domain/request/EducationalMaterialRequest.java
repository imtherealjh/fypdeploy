package com.uow.FYP_23_S1_11.domain.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EducationalMaterialRequest {
    @JsonProperty("materialID")
    private Integer materialID;
    @JsonProperty("title")
    private String title;
    @JsonProperty("content")
    private String content;
}
