package com.uow.FYP_23_S1_11.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.uow.FYP_23_S1_11.domain.request.CalendarRequest;

import jakarta.activation.DataHandler;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import jakarta.mail.util.ByteArrayDataSource;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class EmailServiceImpl implements EmailService {
    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String sender;

    @Async
    @Override
    public void sendEmail(String from, String senderName, String to, String subject, String content)
            throws MessagingException, UnsupportedEncodingException {
        MimeMessage _message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(_message);

        helper.setFrom(from, senderName);
        helper.setTo(to);
        helper.setSubject(subject);

        helper.setText(content, true);

        mailSender.send(_message);
    }

    @Async
    @Override
    public void sendCalendarInvite(CalendarRequest calendarReq) throws MessagingException, IOException {
        MimeMessage _message = mailSender.createMimeMessage();
        _message.addHeaderLine("method=REQUEST");
        _message.addHeaderLine("charset=UTF-8");
        _message.addHeaderLine("component=VEVENT");
        _message.setFrom(new InternetAddress(sender));
        _message.addRecipient(Message.RecipientType.TO, new InternetAddress(calendarReq.getToEmail()));
        _message.setSubject("Calendar Invite Request");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd HHmmss");

        StringBuilder builder = new StringBuilder();
        builder.append("BEGIN:VCALENDAR\n" +
                "METHOD:REQUEST\n" +
                "PRODID:Microsoft Exchange Server 2010\n" +
                "VERSION:2.0\n" +
                "BEGIN:VTIMEZONE\n" +
                "TZID:Asia/Kolkata\n" +
                "END:VTIMEZONE\n" +
                "BEGIN:VEVENT\n" +
                "ATTENDEE;ROLE=REQ-PARTICIPANT;RSVP=TRUE:MAILTO:" + calendarReq.getToEmail() + "\n" +
                "ORGANIZER;CN=" + calendarReq.getClinicName() + ":MAILTO:" + calendarReq.getFromEmail() + "\n" +
                "DESCRIPTION;LANGUAGE=en-US:" + calendarReq.getBody() + "\n" +
                "UID:" + UUID.randomUUID().toString() + "\n" +
                "SUMMARY;LANGUAGE=en-US:Discussion\n" +
                "DTSTART:" + formatter.format(calendarReq.getMeetingStartTime()).replace(" ", "T")
                + "\n" +
                "DTEND:" + formatter.format(calendarReq.getMeetingEndTime()).replace(" ", "T")
                + "\n" +
                "CLASS:PUBLIC\n" +
                "PRIORITY:5\n" +
                "DTSTAMP:" + LocalDateTime.now() + "\n" +
                "TRANSP:OPAQUE\n" +
                "STATUS:CONFIRMED\n" +
                "SEQUENCE:$sequenceNumber\n" +
                "LOCATION;LANGUAGE=en-US:Microsoft Teams Meeting\n" +
                "BEGIN:VALARM\n" +
                "DESCRIPTION:REMINDER\n" +
                "TRIGGER;RELATED=START:-PT15M\n" +
                "ACTION:DISPLAY\n" +
                "END:VALARM\n" +
                "END:VEVENT\n" +
                "END:VCALENDAR");

        MimeBodyPart messageBodyPart = new MimeBodyPart();

        messageBodyPart.setHeader("Content-Class", "urn:content-classes:calendarmessage");
        messageBodyPart.setHeader("Content-ID", "calendar_message");
        messageBodyPart.setDataHandler(new DataHandler(
                new ByteArrayDataSource(builder.toString(),
                        "text/calendar;method=REQUEST;name=\"AppointmentInv.ics\"")));

        MimeMultipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);

        _message.setContent(multipart);

        mailSender.send(_message);
        System.out.println("Calendar invite sent");
    }

}
