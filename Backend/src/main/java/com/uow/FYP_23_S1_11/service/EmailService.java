package com.uow.FYP_23_S1_11.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import com.uow.FYP_23_S1_11.domain.request.CalendarRequest;

import jakarta.mail.MessagingException;

public interface EmailService {
    public void sendCalendarInvite(CalendarRequest calendarReq) throws MessagingException, IOException;

    public void sendEmail(String from, String name, String to, String subject, String message)
            throws MessagingException, UnsupportedEncodingException;
}
