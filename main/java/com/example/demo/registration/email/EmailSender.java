package com.example.demo.registration.email;

public interface EmailSender {
    void sendEmail (String email_to, String name, String link, String subject);
}
