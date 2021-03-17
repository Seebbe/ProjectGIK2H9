package com.example.demo.timers;

import com.example.demo.models.Item;
import com.example.demo.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class EndAuctionService {
    private Item item;
    private Connection connection;

    @Autowired
    ItemRepository itemRepository;

    TimerTask task = new EndAuctionTask() {
        public void run() {
            System.out.println(itemRepository.findById(item.getId()));
        }
    };

    public TimerTask getTask() {
        return task;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}