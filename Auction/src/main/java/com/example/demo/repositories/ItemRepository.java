package com.example.demo.repositories;

import com.example.demo.models.Category;
import com.example.demo.models.CompletedAuction;
import com.example.demo.models.Item;
import com.example.demo.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {

    @Transactional
    @Modifying
    @Query("update Item i set i.name = ?1, i.description = ?2, i.startingBid = ?3, i.picture = ?4, i.category = ?5 where i.id = ?6")
    void updateItem(String name, String description, int startingBid, String picture, Category category, int id);

    @Transactional
    @Modifying
    @Query("update Item i set i.name = ?1, i.description = ?2, i.startingBid = ?3, i.picture = ?4, i.category = ?5 where i.id = ?6")
    void updateItems(String name, String description, int startingBid, String picture, Category category1, int id);

    List<Item> findAllByCategory(Category category, PageRequest pagin);

    @Query(value = "SELECT i FROM Item i ORDER BY i.endTime ASC")
    Page<Item> findAllByOrderByEndTimeAsc(PageRequest pagin);

    @Query("Select i from Item i WHERE i.category.id = ?1")
    List<Item> findAllItemsByCategory(int id);

    List<Item> findAllByUser(User user);
}
