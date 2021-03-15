package com.example.demo.controllers;

import com.example.demo.models.Item;
import com.example.demo.models.User;
import com.example.demo.repositories.ItemRepository;
import com.example.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
public class SellerController {
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    UserRepository userRepository;

    @RequestMapping("/seller")
    public String home(Model model){

        return "seller";
    }
    @RequestMapping("/addauktion")
    public String addAuktion(@RequestParam(defaultValue = "-1") String name,
                             @RequestParam(defaultValue = "-1") String description,
                             @RequestParam(defaultValue = "-1") String endtime,
                             @RequestParam(defaultValue = "-1") String startingbid,
                             @RequestParam(defaultValue = "-1") String picture){
        int enabled = 1;

        Item item = new Item(name,description,Integer.parseInt(startingbid),endtime,enabled,picture);
        List<User> users = (List<User>) userRepository.findAll();
        System.out.println(name);
        //Find the user that adds the blog
        System.out.println(description);
        System.out.println(startingbid);

        System.out.println(endtime);

        System.out.println(enabled);
        System.out.println(picture);



        for(User a:users){
            System.out.println(a.getName());
            if(a.getName().equals("User")){
                item.setUser(a);
                a.addItem(item);

                itemRepository.save(item);
                break;
            }
        }


        return "redirect:/seller";
    }
}
