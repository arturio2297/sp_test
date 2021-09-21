package com.example.demo.registration.validators;

import org.springframework.stereotype.Service;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

@Service
public class EmailValidator {
    public static boolean test(String email) {
        boolean result = true;
        try {
            InternetAddress address = new InternetAddress(email);
            address.validate();
        } catch (AddressException ex) {
            result = false;
        }
        return result;
    }
}
