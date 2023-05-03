package com.uow.FYP_23_S1_11.domain.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.uow.FYP_23_S1_11.utils.LongTextSerializer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EduMaterialResponse {
    @JsonProperty("materialId")
    private Integer materialId;

    @JsonProperty("title")
    private String title;

    @JsonProperty("clinicName")
    private String clinicName;

    @JsonSerialize(using = LongTextSerializer.class)
    @JsonProperty("content")
    private String content;
}
