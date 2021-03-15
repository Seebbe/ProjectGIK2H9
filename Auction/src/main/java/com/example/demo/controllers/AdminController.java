package com.example.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/")
public class AdminController {
    @GetMapping("")
    public String index(Model model) {
        model.addAttribute("meddelande", "Jag är en admin!");
        return "test";
    }
}
