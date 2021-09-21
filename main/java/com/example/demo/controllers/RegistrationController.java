package com.example.demo.controllers;

import com.example.demo.registration.RegistrationRequest;
import com.example.demo.registration.RegistrationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegistrationController {

    private RegistrationService registrationService;

    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @GetMapping("/register")
    public String registerForm(Model model) {
        return "registration";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String firstName,
                               @RequestParam String lastName,
                               @RequestParam String email,
                               @RequestParam String password) {
        var rr = new RegistrationRequest(
                firstName,
                lastName,
                email,
                password
        );
        System.out.println(firstName + "\n"
                + lastName + "\n"
                + email + "\n"
                + password);
        registrationService.register(rr);
        return "redirect:/login";
    }

    @GetMapping("/register/confirm/token={id}")
    public String confirmToken(@PathVariable(value = "id") String id) {
        registrationService.confirmToken(id);
        return "redirect:/login";
    }
}
