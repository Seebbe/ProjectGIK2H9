package com.example.demo.timers;

import com.example.demo.models.Item;
import com.example.demo.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.TimerTask;

@Service
public class EndAuctionTask extends TimerTask {
    private Item item;
    @Autowired
    ItemRepository itemRepository;

    @Override
    public void run() {
        System.out.println("Här");
            item.setEnabled(0);
            itemRepository.save(item);
            System.out.println(item.getName() + " auction end time has ended. Set to enabled = 0");

            //finns det en vinnare? osv
            //koda koda
        System.out.println("Skicka mail till vinnaren om det finns en");
        System.out.println("Lägg till i tabellen completedauction");
    }

    public void setItem(Item item) {
        this.item = item;
    }
}