package com.example.demo.Service;

import org.springframework.stereotype.Service;

import java.util.Observable;
import java.util.Observer;

@Service
public class NotifyBiddersService implements Observer {

    @Override
    public void update(Observable o, Object arg) {
        System.out.println(arg);


        System.out.println("ej");
    }
}
