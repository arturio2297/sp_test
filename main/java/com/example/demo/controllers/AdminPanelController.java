package com.example.demo.controllers;

import com.example.demo.users.UserEntity;
import com.example.demo.users.UserRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AdminPanelController {

    private UserRepository userRepository;

    public AdminPanelController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/adminpanel")
    public String adminPanel(Model model) {
        Iterable<UserEntity> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "adminpanel";
    }

}
