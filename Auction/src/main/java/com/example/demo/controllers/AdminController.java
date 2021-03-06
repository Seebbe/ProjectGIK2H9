package com.example.demo.controllers;

import com.example.demo.models.Category;
import com.example.demo.models.Item;
import com.example.demo.models.User;
import com.example.demo.repositories.CategoryRepository;
import com.example.demo.repositories.ItemRepository;
import com.example.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/")
public class AdminController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    CategoryRepository categoryRepository;

    //Hämtar alla items/users till adminsidan
    @GetMapping("")
    public String index(Model model,@RequestParam(required = false) Integer id) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);

        List<Category> categories= categoryRepository.findAll();
        model.addAttribute("category", categories);

        return "admin";
    }

    //Delete sektion
    @GetMapping(value = "/deleteItem/{id}")
    public String deleteItem(@PathVariable Integer id){
        itemRepository.deleteById(id);
        return "redirect:/admin/";
    }

    @GetMapping(value = "/deleteUser/{id}")
    public String deleteUser(@PathVariable Integer id){
        userRepository.deleteById(id);
        return "redirect:/admin/";
    }

    //Update sektion
    @GetMapping("/updateitem")
    public String updateItem(@RequestParam(defaultValue = "-1") String name,
                             @RequestParam(defaultValue = "-1") String description,
                             @RequestParam(defaultValue = "-1") String startingBid,
                             @RequestParam(defaultValue = "-1") String picture,
                             @RequestParam(defaultValue = "-1") String id,
                             @RequestParam(defaultValue = "-1") String category) {
        Category category1 = categoryRepository.findByTitle(category);
        itemRepository.updateItems(name,description,Integer.parseInt(startingBid),picture,category1,Integer.parseInt(id));

        return "redirect:/admin/";
    }

    @GetMapping("/updateUser")
    public String updateUser(@RequestParam(defaultValue = "-1") String name,
                             @RequestParam(defaultValue = "-1") String email,
                             @RequestParam(defaultValue = "-1") String description,
                             @RequestParam(defaultValue = "-1") String id){
        userRepository.updateUser(name,email,description,Integer.parseInt(id));

        return "redirect:/admin/";
    }
}
