package com.example.demo.controllers;

import com.example.demo.models.User;
import com.example.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/")
public class AdminController {
    @Autowired
    UserRepository userRepository;

    @GetMapping("")
    public String index(Model model) {
        User loggedInUser = userRepository.findByEmail(MainController.getLoggedInUser("admin"));
        model.addAttribute("admin", loggedInUser);
        model.addAttribute("meddelande", "Jag är en admin!");
        return "test";
    }
}
