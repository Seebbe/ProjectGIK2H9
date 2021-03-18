package com.example.demo.controllers;

import com.example.demo.models.Bid;
import com.example.demo.models.Item;
import com.example.demo.models.User;
import com.example.demo.repositories.BidRepository;
import com.example.demo.repositories.ItemRepository;
import com.example.demo.repositories.UserRepository;

import com.example.demo.service.NotifyBiddersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/bidder/")
public class BidderController {
    List<Item> items = new ArrayList<>();

    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    BidRepository bidRepository;

    @Autowired
    NotifyBiddersService notifyBiddersService;

    @GetMapping("")
    public String index() {
        return "test";
    }

    @PostMapping("/laybid")
    public String layBid(Model model, @RequestParam(name = "id") Integer id, @ModelAttribute Bid placedBid) {

        Bid newBid = new Bid();
        User loggedInUser = userRepository.findByEmail(MainController.getLoggedInUser());
        Item currentItem = itemRepository.findById(id).get();
        Item currentItemSave = itemRepository.findById(id).get();
       // newBid.setItem(currentItem);
        newBid.setDate(new Date());
        newBid.setUser(loggedInUser);
        newBid.setPrice(placedBid.getPrice());

        Integer highestBid = 0;
        for (Bid tempBid : bidRepository.findAllByItemOrderByPrice(currentItem)) {
            if (highestBid < tempBid.getPrice())
                highestBid = tempBid.getPrice();
        }
        if(!currentItem.auctionHasEnded()) {
            if (placedBid.getPrice() < currentItem.getStartingBid()) {
                model.addAttribute("message", "You can't place bid lower than the starting bid.");
                model.addAttribute("loggedin",loggedInUser);
                return "genericmessage";
            }

            if (placedBid.getPrice() <= highestBid) {
                model.addAttribute("message", "You can't place bid lower than the current highest bid.");
                model.addAttribute("loggedin",loggedInUser);
                return "genericmessage";
            }
            currentItemSave.addObserver(notifyBiddersService);
            currentItem.addBid(newBid);
            itemRepository.save(currentItem);
        }
        else {
            model.addAttribute("message", "The auction is over.");
            return "genericmessage";
        }

        model.addAttribute("item", itemRepository.findById(id).get());
        model.addAttribute("top3bids", bidRepository.findTop3ByItemOrderByPriceDesc(currentItem));
        return "singleitem";
    }
}
