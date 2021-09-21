package com.example.demo.registration.email;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Service
public class EmailService implements EmailSender {

    private final static String PATH = "***";
    private final static String PASSWORD = "***";
    private final static String USERNAME = "***";

    @Override
    public void sendEmail(String email_to, String name, String link, String subject) {
        Properties properties = System.getProperties();

        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(USERNAME,PASSWORD);
            }
        });
        session.setDebug(true);
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(USERNAME));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(email_to));
            message.setSubject(subject);
            message.setContent(getEmailContent(name, link), "text/html");
            Transport.send(message);
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }

    private String getEmailContent(String name, String link) {
        String email_template = "";
        File file = new File(PATH);
        try (var is = new FileInputStream(file)) {
            email_template = new String(is.readAllBytes());
        } catch (IOException e) { e.printStackTrace(); }
        return email_template
                .replace("{#n}",name)
                .replace("{#l}",link);
    }
}
