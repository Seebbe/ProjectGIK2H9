package com.example.demo.controllers;

import com.example.demo.models.Item;
import com.example.demo.models.User;
import com.example.demo.repositories.ItemRepository;
import com.example.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller

@RequestMapping("/seller")
public class SellerController {
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    UserRepository userRepository;

    @GetMapping("/add")
    public String home(Model model){

        return "seller";
    }
    @GetMapping("/addauktion")
    public String addAuktion(@RequestParam(defaultValue = "-1") String name,
                             @RequestParam(defaultValue = "-1") String description,
                             @RequestParam(defaultValue = "-1") String endtime,
                             @RequestParam(defaultValue = "-1") String startingprice,
                             @RequestParam(defaultValue = "-1") String picture){
        int enabled = 1;

        Item item = new Item(name,description,Integer.parseInt(startingprice),endtime,enabled,picture);
        List<User> users = (List<User>) userRepository.findAll();
        System.out.println(name);
        //Find the user that adds the blog
        System.out.println(description);
        System.out.println(startingprice);

        System.out.println(endtime);

        System.out.println(enabled);
        System.out.println(picture);
        //User loggedInUser = userRepository.findByEmail(MainController.getLoggedInUser("admin"));


        for(User a:users){
            System.out.println(a.getName());
            if(a.getName().equals("Adminsson")){
                item.setUser(a);
                a.addItem(item);

                itemRepository.save(item);
                break;
            }
        }


        return "redirect:/seller";
    }

    @GetMapping("")
    public String index() {
        return "test";
    }

}



