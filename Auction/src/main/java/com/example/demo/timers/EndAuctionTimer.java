package com.example.demo.timers;

import com.example.demo.models.Item;
import com.example.demo.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Timer;
import java.util.TimerTask;

@Service
public class EndAuctionTimer {

    @Autowired
    EndAuctionService endAuctionService;

    @Autowired
    ItemRepository itemRepository;

    public void startTimer(Item item) {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                item.setEnabled(0);
                System.out.println(item.getName() + " ändrad!" + item.getEnabled());
                itemRepository.save(item);
            }
        };

        //endAuctionTask.setItem(addedItem);
        Timer timer = new Timer();
        timer.schedule(task, item.getEndTime());
    }
}