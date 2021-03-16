package com.example.demo.controllers;
import org.springframework.stereotype.Controller;


@Controller
public class MainController {

    public static String getLoggedInUser() {
        String userName = "";
        /*Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            userName = ((UserDetails)principal).getUsername();
        } else {
            userName = "-";
        }

         */

        //hårdkodar en user som returneras
        userName = "seller@user.com";

        return userName;
    }

}