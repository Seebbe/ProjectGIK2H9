package com.example.demo.controllers;

import com.example.demo.models.User;
import com.example.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {
    @Autowired
    private UserRepository userRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void runOnStart(){
        //Skapar upp användare
    User u1 = new User("Adminsson","admin@admin.se","$2y$12$2tYyMIWKcov.yX/9TSmzcenjngVGK4UAQZ4AAIobRIiSsbbo7CLOe","Cool snubbe","ROLE_ADMIN");
    User u2 = new User("En säljare","seller@user.com","$2y$12$2tYyMIWKcov.yX/9TSmzcenjngVGK4UAQZ4AAIobRIiSsbbo7CLOe","Säljer som smör","ROLE_SELLER");
    User u3 = new User("En budare","bidder@user2.com","$2y$12$2tYyMIWKcov.yX/9TSmzcenjngVGK4UAQZ4AAIobRIiSsbbo7CLOe","Han köper allt","ROLE_BIDDER");
    userRepository.save(u1);
    userRepository.save(u2);
    userRepository.save(u3);


    //PASSWORD = 123
    //return "test";
    }
    @GetMapping(value = "/Itemsite")
        public String GetItemSite() {
        return "Item";
    }

    public static String getLoggedInUser(){
        String userName = "";
        /*Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            userName = ((UserDetails)principal).getUsername();
        } else {
            userName = "-";
        }

         */

        //hårdkodar en user som returneras
            userName =  "seller@user.com";

        return userName;
    }
}
