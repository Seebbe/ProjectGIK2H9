package com.example.demo.service;

import com.example.demo.models.Bid;
import com.example.demo.models.Item;
import com.example.demo.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Observable;
import java.util.Observer;

@Service
public class NotifyBiddersService implements Observer {

    @Autowired
    SendNotficationService sendNotficationService;

    @Autowired
    ItemRepository itemRepository;

    @Override
    public void update(Observable o, Object arg) {
        Bid bid = (Bid)arg;
        Item savedItem = itemRepository.findById(bid.getItem().getId()).get();

       String emailBodyText = String.format("Hello %s! You placed a bid on the item %s for %d SEK.  \nThe end time for the auction is %s. \nYours sincerely, The Auction company",
                bid.getUser().getName(), savedItem.getName(), bid.getPrice(),  savedItem.getFormattedDate());

        sendNotficationService.sendEmailNotification(bid.getUser().getEmail(), "You placed a bid! auction: " + savedItem.getName(), emailBodyText);
    }
}
