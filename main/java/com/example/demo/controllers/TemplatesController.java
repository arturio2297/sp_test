package com.example.demo.controllers;

import com.example.demo.registration.RegistrationRequest;
import com.example.demo.registration.RegistrationService;
import com.example.demo.users.UserEntity;
import com.example.demo.users.UserRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class TemplatesController {
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_WRITER', 'ROLE_VIEWER')")
    @GetMapping("/home")
    public String home(Model model) {
        return "home";
    }

    @GetMapping("/")
    public String index(Model model) {
        return "index";
    }

    @GetMapping("/login")
    public String login(Model model) {
        return "login";
    }
}
