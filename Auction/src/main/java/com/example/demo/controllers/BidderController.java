package com.example.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/bidder/")
public class BidderController {
    @GetMapping("")
    public String index() {
        return "test";
    }
}
