package com.example.demo.controllers;

import com.example.demo.models.Bid;
import com.example.demo.models.Category;
import com.example.demo.models.Item;
import com.example.demo.models.User;
import com.example.demo.repositories.BidRepository;
import com.example.demo.repositories.CategoryRepository;
import com.example.demo.repositories.ItemRepository;
import com.example.demo.repositories.UserRepository;
import org.jboss.jandex.Main;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Calendar;
import java.util.Date;

import static java.util.Comparator.comparing;

@Controller
public class MainController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private BidRepository bidRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void runOnStart() {
        //Skapar upp användare
        User u1 = new User("Adminsson", "admin@admin.se", "$2y$12$2tYyMIWKcov.yX/9TSmzcenjngVGK4UAQZ4AAIobRIiSsbbo7CLOe", "Cool snubbe", "ROLE_ADMIN");
        User u2 = new User("En säljare", "seller@user.com", "$2y$12$2tYyMIWKcov.yX/9TSmzcenjngVGK4UAQZ4AAIobRIiSsbbo7CLOe", "Säljer som smör", "ROLE_SELLER");
        User u3 = new User("En budare", "bidder@user2.com", "$2y$12$2tYyMIWKcov.yX/9TSmzcenjngVGK4UAQZ4AAIobRIiSsbbo7CLOe", "Han köper allt", "ROLE_BIDDER");
        userRepository.save(u1);
        Item a4 = new Item("bb", "Vill någon köpa mä?", 25, new Date(), 1, "https://i1.adis.ws/i/canon/EOS-r5_Martin_Bissig_Lifestyle_hero-e90f9dd2-be19-11ea-b23c-8c04ba195b5f?w=100%&sm=aspect&aspect=16:9&qlt=80&fmt=jpg&fmt.options=interlaced&bg=rgb(255,255,255)");

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
    }

    @GetMapping(value = "/Itemsite")
    public String GetItemSite() {
        return "Item";
    }

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

    //Singelsida för ett auktionsobjekt
    @GetMapping("/auctionitem")
    public String getSingleItemPage(Model model, @RequestParam(name = "id") Integer id) {
        //hårdkodar in 4 bud
        User user = userRepository.findByEmail(getLoggedInUser());
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

        model.addAttribute("item", itemRepository.findById(id).get());
        model.addAttribute("top3bids", bidRepository.findTop3ByItemOrderByPriceDesc(item));

        return "singleitem";
    }
}