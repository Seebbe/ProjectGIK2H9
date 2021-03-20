package com.example.demo.controllers;



import com.example.demo.models.Category;
import com.example.demo.models.Item;
import com.example.demo.models.User;
import com.example.demo.repositories.BidRepository;
import com.example.demo.repositories.CategoryRepository;
import com.example.demo.repositories.ItemRepository;
import com.example.demo.repositories.UserRepository;
import com.example.demo.service.SendNotficationService;
import com.example.demo.timers.EndAuctionTimer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Controller

@RequestMapping("/seller")
public class SellerController {
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    EndAuctionTimer endAuctionTimer;
    @Autowired
    BidRepository bidRepository;
    @Autowired
    SendNotficationService sendNotficationService;


    @GetMapping("/add")
    public String home(Model model){
        User loggedInUser = userRepository.findByEmail(MainController.getLoggedInUser());
        model.addAttribute("items",itemRepository.findAllByUser(loggedInUser));
        model.addAttribute("category",categoryRepository.findAll());
        return "seller";
    }


    @GetMapping("/addauktion")
    public String addAuktion(@RequestParam(defaultValue = "-1") String name,
                             @RequestParam(defaultValue = "-1") String description,
                             @RequestParam(defaultValue = "-1") String endtime,
                             @RequestParam(defaultValue = "-1") Integer startingprice,
                             @RequestParam(defaultValue = "-1") String picture,
                             @RequestParam(defaultValue = "-1") Integer category){
        int enabled = 1;

        Item item = new Item(name,description,startingprice,new Date(),enabled,picture);
        item.setCategory(categoryRepository.findById(category).get());
        List<Category>categories = categoryRepository.findAll();
        User loggedInUser = userRepository.findByEmail(MainController.getLoggedInUser());
        loggedInUser.addItem(item);

        item = itemRepository.save(item);

        //aktivera timern som utför ändring av enable till 0 och skickar mail till vinnaren om det finns en när
        //endTime har gått ut
        endAuctionTimer.startTimer(item);
        //skicka bekräftelse till säljaren att objektet är utlagt
        sendNotficationService.sendEmailNotification(loggedInUser.getEmail(),"Item",item.getName() + "has been added with the starting price" + item.getStartingBid());

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



