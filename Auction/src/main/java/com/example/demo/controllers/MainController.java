package com.example.demo.controllers;

import com.example.demo.models.User;
import com.example.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {
    @Autowired
    private UserRepository userRepository;

    @EventListener(ApplicationReadyEvent.class)
    public String runOnStart(Model model){
        //Skapar upp användare
    User u1 = new User("Admin","admin@admin.se","$2y$12$2tYyMIWKcov.yX/9TSmzcenjngVGK4UAQZ4AAIobRIiSsbbo7CLOe","ROLE_ADMIN");
    User u2 = new User("User","user@user.com","$2y$12$2tYyMIWKcov.yX/9TSmzcenjngVGK4UAQZ4AAIobRIiSsbbo7CLOe","ROLE_USER");
    User u3 = new User("User2","user2@user2.com","$2y$12$2tYyMIWKcov.yX/9TSmzcenjngVGK4UAQZ4AAIobRIiSsbbo7CLOe","ROLE_USER");
    userRepository.save(u1);
    userRepository.save(u2);
    userRepository.save(u3);
    //PASSWORD = 123
    return "test";
}
}
