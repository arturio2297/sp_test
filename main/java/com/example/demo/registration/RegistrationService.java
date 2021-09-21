package com.example.demo.registration;

import com.example.demo.registration.validators.PasswordValidator;
import com.example.demo.security.PasswordEncoder;
import com.example.demo.users.Roles;
import com.example.demo.users.UserEntity;
import com.example.demo.registration.email.EmailService;
import com.example.demo.registration.token.TokenEntity;
import com.example.demo.registration.token.TokenService;
import com.example.demo.registration.validators.EmailValidator;
import com.example.demo.users.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static com.example.demo.users.Roles.*;

@Service
public class RegistrationService {

    private UserService userService;
    private EmailValidator emailValidator;
    private PasswordValidator passwordValidator;
    private TokenService tokenService;
    private EmailService emailService;

    public RegistrationService(UserService userService,
                               EmailValidator emailValidator,
                               PasswordValidator passwordValidator,
                               TokenService tokenService,
                               EmailService emailService) {
        this.userService = userService;
        this.emailValidator = emailValidator;
        this.passwordValidator = passwordValidator;
        this.tokenService = tokenService;
        this.emailService = emailService;
    }

    private final String CONFIRM_LINK = "http://localhost:8080/register/confirm/token=";


    public void register(RegistrationRequest request) {
        var isValidEmail = emailValidator.test(request.getEmail());
        if (!isValidEmail) {
            throw new IllegalStateException("email not valid");
        }
        var isValidPassword = passwordValidator.test(request.getPassword());
        if (!isValidPassword) {
            throw new IllegalStateException("password not valid");
        }

        String token = userService.singUpUser(
                new UserEntity(
                        request.getFirstName(),
                        request.getLastName(),
                        request.getEmail(),
                        request.getPassword(),
                        ROLE_VIEWER,
                        ROLE_VIEWER.getGrantedAuthorities())
        );

        String link = CONFIRM_LINK + token;
        emailService.sendEmail(request.
                        getEmail(),
                        request.getFirstName(),
                        link,
                "Confirm registration on spring security test");
    }

    @Transactional
    public void confirmToken(String token) {
        TokenEntity tokenEntity = tokenService
                .getToken(token)
                    .orElseThrow(()->
                        new IllegalStateException("token not found"));
        if (tokenEntity.getConfirmedTime() != null) {
            throw new IllegalStateException("email already confirmed");
        }

        LocalDateTime expiredTime = tokenEntity.getExpiresTime();

        if (expiredTime.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("token expired");
        }

        if(tokenService.updateConfirmedTime(token)) {
            userService.enableUserAccount(
                    tokenEntity.
                            getUser().
                                getUsername());
        }
    }
}
