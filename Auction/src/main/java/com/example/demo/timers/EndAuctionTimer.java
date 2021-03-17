package com.example.demo.timers;

import com.example.demo.models.Item;
import org.springframework.stereotype.Component;

import java.util.Timer;

@Component
public class EndAuctionTimer {
    Item addedItem;

    public void startTimer() {
        //endAuctionTask.setItem(addedItem);
        Timer timer = new Timer();
        EndAuctionTask endAuctionTask2 = new EndAuctionTask();
        endAuctionTask2.setItem(addedItem);
        timer.schedule(endAuctionTask2, addedItem.getEndTime());

    }

    public void setAddedItem(Item addedItem) {
        this.addedItem = addedItem;
    }

}