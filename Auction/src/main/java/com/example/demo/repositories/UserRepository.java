package com.example.demo.repositories;

import com.example.demo.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface UserRepository extends JpaRepository<User, Integer> {

    public User findByEmail(String email);

    @Transactional
    @Modifying
    @Query("update User u set u.name = ?1, u.email = ?2, u.description = ?3 where u.id = ?4")
    void updateUser(String name,String email,String description,int id);
}
