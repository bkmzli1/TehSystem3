package ru.tehsystem.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
public class Main {
    @GetMapping("/reg")
    public String main() {
        return "index";
    }
    @GetMapping("/login")
    public String loginGet(Model model){
        return "index";
    }
    @GetMapping("/profile")
    public String profileGet(Model model){
        return "index";
    }
    @GetMapping("/")
    public String home(){
        return "index";
    }
}