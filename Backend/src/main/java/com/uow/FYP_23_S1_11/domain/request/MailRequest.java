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
public class MailRequest {
    @JsonProperty("recipient")
    private String recipient;
    @JsonProperty("msgBody")
    private String msgBody;
    @JsonProperty("subject")
    private String subject;
    @JsonProperty("attachment")
    private String attachment;
}
