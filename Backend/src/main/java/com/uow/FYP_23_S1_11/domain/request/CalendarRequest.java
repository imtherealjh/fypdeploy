package com.uow.FYP_23_S1_11.domain.request;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CalendarRequest {
    private String toEmail;
    private String subject;
    private String body;
    @DateTimeFormat(iso = ISO.DATE_TIME)
    private LocalDateTime meetingStartTime;
    @DateTimeFormat(iso = ISO.DATE_TIME)
    private LocalDateTime meetingEndTime;
}
