package com.uow.FYP_23_S1_11.service;

import java.io.UnsupportedEncodingException;

import jakarta.mail.MessagingException;

public interface EmailService {
    public void sendEmail(String from, String name, String to, String subject, String message)
            throws MessagingException, UnsupportedEncodingException;
}
