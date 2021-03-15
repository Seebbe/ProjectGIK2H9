package com.example.demo.repositories;

import com.example.demo.models.Category;
import com.example.demo.models.CompletedAuction;
import com.example.demo.models.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {

    @Transactional
    @Modifying
    @Query("update Item i set i.name = ?1, i.description = ?2, i.startingBid = ?3, i.picture = ?4, i.category = ?5 where i.id = ?6")
    void updateItem(String name, String description, int startingBid, String picture, Category category, int id);
}
