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
        Item a1 = new Item("aa","awdawdadawdawdadada",20,"20:00",1,"https://i1.adis.ws/i/canon/EOS-r5_Martin_Bissig_Lifestyle_hero-e90f9dd2-be19-11ea-b23c-8c04ba195b5f?w=100%&sm=aspect&aspect=16:9&qlt=80&fmt=jpg&fmt.options=interlaced&bg=rgb(255,255,255)");
        Item a2 = new Item("bb","bbbbbbbbbbbbbbbb",20,"20:00",1,"https://i1.adis.ws/i/canon/EOS-r5_Martin_Bissig_Lifestyle_hero-e90f9dd2-be19-11ea-b23c-8c04ba195b5f?w=100%&sm=aspect&aspect=16:9&qlt=80&fmt=jpg&fmt.options=interlaced&bg=rgb(255,255,255)");
        Item a3 = new Item("bb","aaaaaaaaaaaaaaaaaa",20,"20:00",1,"https://i1.adis.ws/i/canon/EOS-r5_Martin_Bissig_Lifestyle_hero-e90f9dd2-be19-11ea-b23c-8c04ba195b5f?w=100%&sm=aspect&aspect=16:9&qlt=80&fmt=jpg&fmt.options=interlaced&bg=rgb(255,255,255)");
        User loggedInUser = userRepository.findByEmail(MainController.getLoggedInUser("seller"));
        loggedInUser.addItem(a1);
        itemRepository.save(a1);
        loggedInUser.addItem(a2);
        itemRepository.save(a2);
        loggedInUser.addItem(a3);
        itemRepository.save(a3);
        model.addAttribute("items",itemRepository.findAll());

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


        User loggedInUser = userRepository.findByEmail(MainController.getLoggedInUser());
        loggedInUser.addItem(item);
        itemRepository.save(item);



        return "redirect:/seller";
    }

    @GetMapping("")
    public String index() {
        return "test";
    }

}



