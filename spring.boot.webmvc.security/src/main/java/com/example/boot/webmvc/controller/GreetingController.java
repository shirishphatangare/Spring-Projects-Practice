package com.example.boot.webmvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GreetingController {

    @GetMapping("/greeting")
    public String greeting(@RequestParam(name="username", required=false, defaultValue="World") String username, Model model) {
        model.addAttribute("username", username);
        return "greeting";
    }
}