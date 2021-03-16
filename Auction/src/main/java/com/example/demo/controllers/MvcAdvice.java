package com.example.demo.controllers;

import com.example.demo.models.User;
import com.example.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class MvcAdvice {
    @Autowired
    UserRepository userRepository;
    //klass för att deklarerea ModelAttribute (globala variabler) som kan nås i alla vyer
    @ModelAttribute("globalLoggedInUser")
    public User getLoggedInUser() {
        User loggedInUser = userRepository.findByEmail(MainController.getLoggedInUser());

        return loggedInUser;
    }
}
