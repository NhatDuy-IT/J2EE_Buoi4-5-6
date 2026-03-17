package com.example.ValidDemoWebsite.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/home")
    public String home() {
        // Chuyển về danh sách sản phẩm làm trang chính của ứng dụng
        return "redirect:/products";
    }
}
