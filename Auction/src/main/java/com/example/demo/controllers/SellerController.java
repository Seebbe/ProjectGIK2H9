package com.example.demo.controllers;

import com.example.demo.Service.SendNotficationService;
import com.example.demo.models.Category;
import com.example.demo.models.Item;
import com.example.demo.models.User;
import com.example.demo.repositories.CategoryRepository;
import com.example.demo.repositories.ItemRepository;
import com.example.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller

@RequestMapping("/seller")
public class SellerController {
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @GetMapping("/add")
    public String home(Model model){
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
        return "seller";
    }
    @Autowired
    SendNotficationService sendNotficationService;

    @GetMapping("/addauktion")
    public String addAuktion(@RequestParam(defaultValue = "-1") String name,
                             @RequestParam(defaultValue = "-1") String description,
                             @RequestParam(defaultValue = "-1") String endtime,
                             @RequestParam(defaultValue = "-1") String startingprice,
                             @RequestParam(defaultValue = "-1") String picture,
                             @RequestParam(defaultValue = "-1") String category){
        int enabled = 1;

        Item item = new Item(name,description,Integer.parseInt(startingprice),new Date(),enabled,picture);
        List<Category>categories = categoryRepository.findAll();
        for(Category c:categories) {
            if (c.getTitle().equals(category)) {
                User loggedInUser = userRepository.findByEmail(MainController.getLoggedInUser());
                item.setCategory(c);

                loggedInUser.addItem(item);
                itemRepository.save(item);

                /*public String sendEmailNotification (Model model){

                    sendNotficationService.sendEmailNotification("bidder@du.se", "Items", "New item added");

                 */
                }


            }
        

        return "redirect:/seller/add";
    }

    @GetMapping("")
    public String index() {
        return "test";
    }
    @GetMapping("/delete/{id}")
    public String deleteItem(@PathVariable Integer id) {
        itemRepository.deleteById(id);
        return "redirect:/seller/add";
    }
    @GetMapping("/updateitem")
    public String updateItem(@RequestParam(defaultValue = "-1") String name,
                             @RequestParam(defaultValue = "-1") String description,
                             @RequestParam(defaultValue = "-1") String startingBid,
                             @RequestParam(defaultValue = "-1") String picture,
                             @RequestParam(defaultValue = "-1") String id,
                             @RequestParam(defaultValue = "-1") String category) {
        Category category1 = categoryRepository.findByTitle(category);
        itemRepository.updateItem(name,description,Integer.parseInt(startingBid),picture,category1,Integer.parseInt(id));

        return "redirect:/seller/add";
    }

}



