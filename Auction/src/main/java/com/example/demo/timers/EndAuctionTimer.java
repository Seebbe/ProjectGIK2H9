package com.example.demo.timers;

import com.example.demo.models.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Timer;

@Service
public class EndAuctionTimer {
    Timer timer = new Timer();
    Item item;
    @Autowired
    EndAuctionTask endAuctionTask;

    public void setItem(Item item) {
        this.item = item;
    }

    public void startTimer() {
        endAuctionTask.setItem(item);
        System.out.println(endAuctionTask);
        timer.schedule(endAuctionTask, item.getEndTime());
    }

}