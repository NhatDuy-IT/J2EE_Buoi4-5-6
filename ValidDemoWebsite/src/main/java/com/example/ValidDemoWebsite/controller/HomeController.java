package com.example.ValidDemoWebsite.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/home")
    public String home(@AuthenticationPrincipal UserDetails userDetails) {
        return "Hello, " + (userDetails != null ? userDetails.getUsername() : "anonymous");
    }
}
