package com.example.demo.registration.validators;

import org.springframework.stereotype.Service;
import java.util.regex.Pattern;

@Service
public class PasswordValidator {

    private final static String REGEX = "^(?=.*[0-9])"
                                        + "(?=.*[a-z])(?=.*[A-Z])"
                                        + "(?=.*[@#$%^&+=])"
                                        + "(?=\\S+$).{8,20}$";

    public boolean test(String password) {
        return Pattern.
                    compile(REGEX).
                        matcher(password).
                            matches();
    }
}
