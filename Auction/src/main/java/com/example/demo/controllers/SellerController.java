package com.example.demo.controllers;


import com.example.demo.Service.SendNotficationService;
import com.example.demo.models.Category;
import com.example.demo.models.Item;
import com.example.demo.models.User;
import com.example.demo.repositories.BidRepository;
import com.example.demo.repositories.CategoryRepository;
import com.example.demo.repositories.ItemRepository;
import com.example.demo.repositories.UserRepository;
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
        if (!endtime.contains("")) {
            
        }
        item = itemRepository.save(item);

        //aktivera timern som utför ändring av enable till 0 och skickar mail till vinnaren om det finns en när
        //endTime har gått ut
        //System.out.println(item);
        endAuctionTimer.startTimer(item);
         sendNotficationService.sendEmailNotification(loggedInUser.getEmail(),"Item",item.getName() + "has been added with the starting price" + item.getStartingBid());

        return "redirect:/seller/add";
    }

    @GetMapping("/addauktiontesttimer")
    public String addAuktion2(@RequestParam(defaultValue = "-1") String name,
                             @RequestParam(defaultValue = "-1") String description,
                             @RequestParam(defaultValue = "-1") String endtime,
                             @RequestParam(defaultValue = "-1") String startingprice,
                             @RequestParam(defaultValue = "-1") String picture,
                             @RequestParam(defaultValue = "-1") Integer category,
                              Model model){


        int enabled = 1;
        String image = "https://thumbs.dreamstime.com/z/stopwatch-mechanical-clock-timer-chrome-isolated-d-chronograph-white-background-50874022.jpg";
        Item item = new Item(name,description,Integer.parseInt(startingprice),new Date(),enabled,picture);
        item = new Item("Testprodukt", "En testprodukt", 400, new Date(), enabled, image);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, 2);
        item.setEndTime(calendar.getTime());
        item.setCategory(categoryRepository.findById(3).get());
        User loggedInUser = userRepository.findByEmail(MainController.getLoggedInUser());
        loggedInUser.addItem(item);
        //spara tillbaka item nu med ett autogenererat id
        item = itemRepository.save(item);
        //aktivera timern som utför ändring av enable till 0 och skickar mail till vinnaren om det finns en när
        //endTime har gått ut
        //System.out.println(item);
        endAuctionTimer.startTimer(item);

        Item item2 = new Item("Testprodukt2", "En testprodukt2", 400, new Date(), enabled, image);
        calendar.add(Calendar.SECOND, 4);
        item2.setEndTime(calendar.getTime());
        item2.setCategory(categoryRepository.findById(3).get());
        loggedInUser.addItem(item2);
        //spara tillbaka item2 nu med ett autogenererat id
        item2 = itemRepository.save(item2);
        //aktivera timern som utför ändring av enable till 0 och skickar mail till vinnaren om det finns en när
        //endTime har gått ut
        endAuctionTimer.startTimer(item2);

        model.addAttribute("item", itemRepository.findById(item.getId()).get());
        model.addAttribute("top3bids", bidRepository.findTop3ByItemOrderByPriceDesc(item));
        return "singleitem";
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



