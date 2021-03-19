package com.example.demo.service;

import com.example.demo.models.Bid;
import com.example.demo.models.Item;
import org.springframework.stereotype.Service;

import java.util.Observable;
import java.util.Observer;

@Service
public class NotifyBiddersService implements Observer {

    @Override
    public void update(Observable o, Object arg) {

        //budet som kommer in: 
        Bid bid = (Bid)arg;
        System.out.println(bid);
        System.out.println(arg);


        System.out.println("ej");
    }
}
