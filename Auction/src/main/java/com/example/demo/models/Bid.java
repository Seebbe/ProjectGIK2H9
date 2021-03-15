package com.example.demo.models;

public class Bid {
    private int id;
    private User bidder;
    private Item item;
    private int price;
    private int adrian;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getBidder() {
        return bidder;
    }

    public void setBidder(User bidder) {
        this.bidder = bidder;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getAdrian() {
        return adrian;
    }

    public void setAdrian(int adrian) {
        this.adrian = adrian;
    }
}
