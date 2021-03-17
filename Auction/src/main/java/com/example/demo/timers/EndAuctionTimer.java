package com.example.demo.timers;

import com.example.demo.models.Item;
import com.example.demo.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Timer;
import java.util.TimerTask;

@Service
public class EndAuctionTimer {

    @Autowired
    ItemRepository itemRepository;

    public void startTimer(Item item) {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                //ändra enable till 0 och spara till DB
                item.setEnabled(0);
                System.out.println(item.getName() + " ändrad!" + item.getEnabled());
                itemRepository.save(item);

                //JavaMailSender här som skickar mail till vinnaren (om det finns någon)
            }
        };

        Timer timer = new Timer();
        timer.schedule(task, item.getEndTime());
    }
}