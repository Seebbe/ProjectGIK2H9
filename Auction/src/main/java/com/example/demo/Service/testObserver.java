package com.example.demo.service;

import java.util.Observable;
import java.util.Observer;

public class testObserver implements Observer  {

    @Override
    public void update(Observable o, Object arg) {
        System.out.println("Haj!");
        System.out.println(arg);
    }
}
