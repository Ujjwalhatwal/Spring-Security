package com.ujjwal.springsecurity.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.*;

@RestController
public class HomeController { 

    @GetMapping("/")
    public String home() {
        return "Welcome to the Home Page!";
    }

    @GetMapping("/health")
    public String health() {
        return "Application is Healthy!";
    }
}
