package com.example.demo.controllers;

import com.example.demo.models.Item;
import com.example.demo.models.User;
import com.example.demo.repositories.ItemRepository;
import com.example.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/admin/")
public class AdminController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    ItemRepository itemRepository;


    @GetMapping("")
    public String index(Model model) {
        User loggedInUser = userRepository.findByEmail(MainController.getLoggedInUser());
        model.addAttribute("admin", loggedInUser);
        model.addAttribute("meddelande", "Jag är en admin!");
        return "test";
    }

    @GetMapping("/items")
    public String GetAllItems(Model model, @RequestParam(required = false) Integer id){
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);

        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);

        return "admin";
    }
}
