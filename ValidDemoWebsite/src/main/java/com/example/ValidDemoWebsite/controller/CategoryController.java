package com.example.ValidDemoWebsite.controller;

import com.example.ValidDemoWebsite.model.Category;
import com.example.ValidDemoWebsite.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/categories")
    public String Index(Model model) {
        model.addAttribute("listcategory", categoryService.getAll());
        return "category/categories";
    }

    @GetMapping("/categories/create")
    public String Create(Model model) {
        model.addAttribute("category", new Category());
        return "category/create";
    }

    @PostMapping("/categories/create")
    public String Create(@Valid Category newCategory, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("category", newCategory);
            return "category/create";
        }
        categoryService.add(newCategory);
        return "redirect:/categories";
    }

    @GetMapping("/categories/delete/{id}")
    public String Delete(@PathVariable int id) {
        categoryService.delete(id);
        return "redirect:/categories";
    }
}

