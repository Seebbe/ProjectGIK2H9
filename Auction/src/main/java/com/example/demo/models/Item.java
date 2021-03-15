package com.example.demo.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String description;
    private Integer startingBid;
    private String endTime;
    private Integer enabled;
    private String picture;

    //Relation to Bids
    @OneToMany(
            mappedBy ="item",
            cascade =CascadeType.ALL,
            orphanRemoval = true
    )
    public List<Bid> bids = new ArrayList<>();

    public void addBid(Bid bid){
        bids.add(bid);
        bid.setItem(this);
    }

    //Relation to Category
    @ManyToOne(fetch = FetchType.LAZY)
    private Category category;

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
    //Relation to User
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    public Item(){

    }
    public Item(String name,String description,int startingBid,String endTime,int enabled,String picture){
        this.name=name;
        this.description=description;
        this.startingBid=startingBid;
        this.endTime=endTime;
        this.enabled=enabled;
        this.picture=picture;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setStartingBid(Integer startingBid) {
        this.startingBid = startingBid;
    }

    public void setEnabled(Integer enabled) {
        this.enabled = enabled;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStartingBid() {
        return startingBid;
    }

    public void setStartingBid(int startingBid) {
        this.startingBid = startingBid;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getEnabled() {
        return enabled;
    }

    public void setEnabled(int enabled) {
        this.enabled = enabled;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
