package com.example.demo.repositories;

import com.example.demo.models.Bid;
import com.example.demo.models.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BidRepository extends JpaRepository<Bid, Integer> {
    List<Bid> findTop3ByItemOrderByPrice(Item item);

}
