package com.example.demo.controllers;

import com.example.demo.models.Category;
import com.example.demo.models.Item;
import com.example.demo.models.User;
import com.example.demo.repositories.BidRepository;
import com.example.demo.repositories.CategoryRepository;
import com.example.demo.repositories.ItemRepository;
import com.example.demo.repositories.UserRepository;
import com.example.demo.timers.EndAuctionTimer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

@Controller

public class HomeController {
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    private BidRepository bidRepository;
    @Autowired
    EndAuctionTimer endAuctionTimer;

    @EventListener(ApplicationReadyEvent.class)
    public void runOnStart() {
        //Skapar upp användare
        User u1 = new User("Adminsson", "admin@admin.se", "$2y$12$6whe.3786xhMmCuwWJj.mevsUru0EbCBVoFZswRbGNkQ/A5sEU3EW", "Cool snubbe", "ROLE_ADMIN",1);
        User u2 = new User("Sebbe", "hassehasse300@gmail.com", "$2y$12$6whe.3786xhMmCuwWJj.mevsUru0EbCBVoFZswRbGNkQ/A5sEU3EW", "Säljer som smör", "ROLE_USER",1);
        User u3 = new User("Marcus", "massus.hda@gmail.com", "$2y$12$6whe.3786xhMmCuwWJj.mevsUru0EbCBVoFZswRbGNkQ/A5sEU3EW", "Han köper allt", "ROLE_USER",1);
        User u4 = new User("Kevin", "kevin@hansmail.se", "$2y$12$6whe.3786xhMmCuwWJj.mevsUru0EbCBVoFZswRbGNkQ/A5sEU3EW", "Gillar det mesta", "ROLE_USER",1);
        userRepository.save(u1);
        Item a4 = new Item("bb", "Vill någon köpa mä?", 25, new Date(), 1, "https://i1.adis.ws/i/canon/EOS-r5_Martin_Bissig_Lifestyle_hero-e90f9dd2-be19-11ea-b23c-8c04ba195b5f?w=100%&sm=aspect&aspect=16:9&qlt=80&fmt=jpg&fmt.options=interlaced&bg=rgb(255,255,255)");
        userRepository.save(u4);
        userRepository.save(u3);
        Category c1 = new Category("Teknik", "Allting om teknik");
        Category c2 = new Category("Bilar", "Allting om bilar");
        Category c3 = new Category("Hem", "Allting om hem");
        Category c4 = new Category("Hobby", "Allting om hobby");
        categoryRepository.save(c1);
        categoryRepository.save(c2);
        categoryRepository.save(c3);
        categoryRepository.save(c4);
        //PASSWORD = 123
        //return "test";
        a4.setCategory(c1);
        u2.addItem(a4);
        userRepository.save(u2);


        Item a1 = new Item("aa","awdawdadawdawdadada",20,new Date(),1,"https://i1.adis.ws/i/canon/EOS-r5_Martin_Bissig_Lifestyle_hero-e90f9dd2-be19-11ea-b23c-8c04ba195b5f?w=100%&sm=aspect&aspect=16:9&qlt=80&fmt=jpg&fmt.options=interlaced&bg=rgb(255,255,255)");
        Item a2 = new Item("bb","bbbbbbbbbbbbbbbb",20,new Date(),1,"https://i1.adis.ws/i/canon/EOS-r5_Martin_Bissig_Lifestyle_hero-e90f9dd2-be19-11ea-b23c-8c04ba195b5f?w=100%&sm=aspect&aspect=16:9&qlt=80&fmt=jpg&fmt.options=interlaced&bg=rgb(255,255,255)");
        Item a3 = new Item("bb","aaaaaaaaaaaaaaaaaa",20,new Date(),1,"https://i1.adis.ws/i/canon/EOS-r5_Martin_Bissig_Lifestyle_hero-e90f9dd2-be19-11ea-b23c-8c04ba195b5f?w=100%&sm=aspect&aspect=16:9&qlt=80&fmt=jpg&fmt.options=interlaced&bg=rgb(255,255,255)");

        a1.setCategory(c2);
        a2.setCategory(c2);
        a3.setCategory(c2);
        a4.setCategory(c2);

        //ändrar datumet till ett datum som gått ut
        a4.setEndTime(Calendar.getInstance().getTime());
        Calendar cNow = Calendar.getInstance();
        //addera 2 dagar till dagens datum
        cNow.add(Calendar.DATE, 2);
        a1.setEndTime(cNow.getTime());
        a2.setEndTime(cNow.getTime());
        a3.setEndTime(cNow.getTime());
        a4.setEndTime(cNow.getTime());

        u2.addItem(a1);
        itemRepository.save(a1);
        u2.addItem(a2);
        itemRepository.save(a2);
        u2.addItem(a3);
        itemRepository.save(a3);
        u2.addItem(a4);
        itemRepository.save(a4);

        //timers
        endAuctionTimer.startTimer(a1);
        endAuctionTimer.startTimer(a2);
        endAuctionTimer.startTimer(a3);
        endAuctionTimer.startTimer(a4);


    }

    @GetMapping("/")
    public String redirectIndex() {

        return "redirect:/allitems?page=0";
    }

    @GetMapping("/allitems")
    public String index(Model model, @RequestParam(name = "page") Integer page) {
        if (page<=0 || page==null) {
            page = 0;
        }

        final int PAGESIZE = 6;
        PageRequest pagin = PageRequest.of(page, PAGESIZE);
        Page<Item> pagedResult = itemRepository.findAllByOrderByEndTimeAsc(pagin);
        List<Item> items = pagedResult.getContent();

        //HashMap som håller alla alla sidor från 0 - max antal sidor
        //räknar en sekvens
        //sätter två värden, första för display andra för att länka till rätt sida
        Map<Integer, Integer> totalPagesPairDisplay = new HashMap<>();
        for (int i = 0; i < pagedResult.getTotalPages(); i++) {
            totalPagesPairDisplay.put(i, i+1);
        }

        //getContent ger oss önskat antal inlägg per gång
        model.addAttribute("currentPageNr", pagedResult.getNumber());
        model.addAttribute("displayCurrentPageNr", pagedResult.getNumber() + 1);
        model.addAttribute("nextPageNumber", page+1);
        model.addAttribute("previousPageNumber", page-1);
        model.addAttribute("totalPages", pagedResult.getTotalPages());
        model.addAttribute("totalItems", pagedResult.getTotalElements());
        model.addAttribute("hasNext", pagedResult.hasNext());
        model.addAttribute("hasPrevious", pagedResult.hasPrevious());
        model.addAttribute("showingNrOfPosts", PAGESIZE);


        model.addAttribute("currentCategory", new Category("All items", ""));
        model.addAttribute("totalPagesPairDisplay", totalPagesPairDisplay.entrySet());
        model.addAttribute("items",items);
        model.addAttribute("category",categoryRepository.findAll());

        return "home";
    }

    @GetMapping("/home/{id}")
    public String findByCategory(Model model,@PathVariable Integer id) {

        /*Category category = categoryRepository.findByTitle("Teknik");
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


        loggedInUser.addItem(a1);
        itemRepository.save(a1);
        loggedInUser.addItem(a2);
        itemRepository.save(a2);
        loggedInUser.addItem(a3);
        itemRepository.save(a3);
        loggedInUser.addItem(a4);
        itemRepository.save(a4);*/
       /* Category c1 = new Category("Teknik","Allting om teknik");
        Category c2 = new Category("Bilar","Allting om bilar");
        Category c3 = new Category("Hem","Allting om hem");
        Category c4 = new Category("Hobby","Allting om hobby");
        categoryRepository.save(c1);
        categoryRepository.save(c2);
        categoryRepository.save(c3);
        categoryRepository.save(c4);*/
        //List<Item> items = (List<Item>) itemRepository.findByCategoryId(id);

        /*Category catTemp = categoryRepository.findById(id).get();
        System.out.println(itemRepository.findAllByCategory(catTemp));

         */
        User loggedInUser = userRepository.findByEmail(MainController.getLoggedInUser());
        model.addAttribute("loggedin",loggedInUser);
        model.addAttribute("currentCategory", categoryRepository.findById(id).get());
        model.addAttribute("items",itemRepository.findAllItemsByCategory(id));
        model.addAttribute("category",categoryRepository.findAll());

        return "home";
    }

    @GetMapping(value = "/Itemsite")
    public String GetItemSite() {
        return "Item";
    }

    //Singelsida för ett auktionsobjekt
    @GetMapping("/auctionitem")
    public String getSingleItemPage(Model model, @RequestParam(name = "id") Integer id) {
        //hårdkodar in 4 bud
        User user = userRepository.findByEmail(MainController.getLoggedInUser());
        Item item = itemRepository.findById(id).get();
       /* Bid bid1 = new Bid(26, new Date(), user, item);
        Bid bid2 = new Bid(55, new Date(), user, item);
        Bid bid3 = new Bid(56, new Date(), user, item);
        Bid bid4 = new Bid(57, new Date(), user, item);
        item.addBid(bid1);
        item.addBid(bid2);
        item.addBid(bid3);
        item.addBid(bid4);
        itemRepository.save(item);

        */
        User loggedInUser = userRepository.findByEmail(MainController.getLoggedInUser());
        model.addAttribute("loggedin",loggedInUser);
        model.addAttribute("item", itemRepository.findById(id).get());
        model.addAttribute("top3bids", bidRepository.findTop3ByItemOrderByPriceDesc(item));
        return "singleitem";
    }
}