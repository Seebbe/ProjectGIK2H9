package com.example.demo.timers;

import com.example.demo.models.Bid;
import com.example.demo.models.Item;
import com.example.demo.repositories.BidRepository;
import com.example.demo.repositories.ItemRepository;
import com.example.demo.service.NotifyBiddersService;
import com.example.demo.service.SendNotficationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static java.util.Comparator.comparing;

@Repository
public class EndAuctionTimer {

    @Autowired
    ItemRepository itemRepository;
    @Autowired
    BidRepository bidRepository;
    @Autowired
    SendNotficationService sendNotficationService;

    public void startTimer(Item item) {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Item savedEndedAuctionItem;
                List<Bid> bids;
                savedEndedAuctionItem = itemRepository.findById(item.getId()).get();

                //ändra enable till 0 och spara till DB
                savedEndedAuctionItem.setEnabled(0);
                System.out.println(savedEndedAuctionItem.getName() + " ändrad! Enabled: " + savedEndedAuctionItem.getEnabled());
                //System.out.println(bidRepository.findAllByItemOrderByPrice(item));
                savedEndedAuctionItem = itemRepository.save(savedEndedAuctionItem);
                bids = bidRepository.findAllByItemOrderByPrice(item);

                Bid highestBid = null;
                //det finns en vinnare
                if (!(bids.isEmpty())) {
                    highestBid = bids.stream().max(comparing(Bid::getPrice)).get();
                    System.out.println("Number of bids: " + bids.size());
                    System.out.println("Highest bid: " + highestBid.getPrice());
                    System.out.println("We have a winner: " + highestBid.getUser().getName());
                    System.out.println("Mail: " + highestBid.getUser().getEmail());

                    //JavaMailSender här som skickar mail till vinnaren (om det finns någon)
                    String emailBodyText = String.format("Hello %s! You won the auction with item %s with the winning bid %d SEK. \nThere were %o bids. Please pay. \nYours sincerely, The Auction company",
                            highestBid.getUser().getName(), savedEndedAuctionItem.getName(), highestBid.getPrice(), bids.size());
                    sendNotficationService.sendEmailNotification(highestBid.getUser().getEmail(), "You've won! auction: " + savedEndedAuctionItem.getName(), emailBodyText);
                } else {
                    System.out.println("No winner");
                }
            }
        };
        timer.schedule(task, item.getEndTime());
    }
}