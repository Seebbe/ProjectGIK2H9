package com.example.demo.controllers;

import com.example.demo.models.Category;
import com.example.demo.models.Item;
import com.example.demo.models.User;
import com.example.demo.repositories.CategoryRepository;
import com.example.demo.repositories.ItemRepository;
import com.example.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Calendar;
import java.util.Date;

@Controller

public class HomeController {
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @GetMapping("/")
    public String index(Model model) {
        Category category = categoryRepository.findByTitle("Teknik");
        Item a1 = new Item("aa","awdawdadawdawdadada",20,new Date(),1,"https://i1.adis.ws/i/canon/EOS-r5_Martin_Bissig_Lifestyle_hero-e90f9dd2-be19-11ea-b23c-8c04ba195b5f?w=100%&sm=aspect&aspect=16:9&qlt=80&fmt=jpg&fmt.options=interlaced&bg=rgb(255,255,255)");
        Item a2 = new Item("bb","bbbbbbbbbbbbbbbb",20,new Date(),1,"https://i1.adis.ws/i/canon/EOS-r5_Martin_Bissig_Lifestyle_hero-e90f9dd2-be19-11ea-b23c-8c04ba195b5f?w=100%&sm=aspect&aspect=16:9&qlt=80&fmt=jpg&fmt.options=interlaced&bg=rgb(255,255,255)");
        Item a3 = new Item("bb","aaaaaaaaaaaaaaaaaa",20,new Date(),1,"https://i1.adis.ws/i/canon/EOS-r5_Martin_Bissig_Lifestyle_hero-e90f9dd2-be19-11ea-b23c-8c04ba195b5f?w=100%&sm=aspect&aspect=16:9&qlt=80&fmt=jpg&fmt.options=interlaced&bg=rgb(255,255,255)");
        Item a4 = new Item("bb","Vill någon köpa mä?",25,new Date(),1,"https://i1.adis.ws/i/canon/EOS-r5_Martin_Bissig_Lifestyle_hero-e90f9dd2-be19-11ea-b23c-8c04ba195b5f?w=100%&sm=aspect&aspect=16:9&qlt=80&fmt=jpg&fmt.options=interlaced&bg=rgb(255,255,255)");

        category.addItem(a1);
        category.addItem(a2);
        category.addItem(a3);
        category.addItem(a4);

        //ändrar datumet till ett datum som gått ut
        a4.setEndTime(Calendar.getInstance().getTime());

        User loggedInUser = userRepository.findByEmail(MainController.getLoggedInUser());
        model.addAttribute("loggedin",loggedInUser);
        loggedInUser.addItem(a1);
        itemRepository.save(a1);
        loggedInUser.addItem(a2);
        itemRepository.save(a2);
        loggedInUser.addItem(a3);
        itemRepository.save(a3);
        loggedInUser.addItem(a4);
        itemRepository.save(a4);
       /* Category c1 = new Category("Teknik","Allting om teknik");
        Category c2 = new Category("Bilar","Allting om bilar");
        Category c3 = new Category("Hem","Allting om hem");
        Category c4 = new Category("Hobby","Allting om hobby");
        categoryRepository.save(c1);
        categoryRepository.save(c2);
        categoryRepository.save(c3);
        categoryRepository.save(c4);*/
        model.addAttribute("items",itemRepository.findAll());
        model.addAttribute("category",categoryRepository.findAll());

        return "home";
    }
}