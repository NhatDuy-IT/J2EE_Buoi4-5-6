package com.example.ValidDemoWebsite.controller;

import com.example.ValidDemoWebsite.model.Category;
import com.example.ValidDemoWebsite.model.Product;
import com.example.ValidDemoWebsite.service.CategoryService;
import com.example.ValidDemoWebsite.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class ProductController {

    @GetMapping("/")
    public String Home() {
        return "redirect:/products";
    }

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/products")
    public String Index(Model model) {
        model.addAttribute("listproduct", productService.getAll());
        return "product/products";
    }

    @GetMapping("/products/create")
    public String Create(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryService.getAll());
        return "product/create";
    }

    @PostMapping("/products/create")
    public String Create(@Valid Product newProduct,
                         BindingResult result,
                         @RequestParam(name = "categoryId", required = false) Integer categoryId,
                         @RequestParam("imageProduct") MultipartFile imageProduct,
                         Model model) {
        if (result.hasErrors()) {
            model.addAttribute("product", newProduct);
            model.addAttribute("categories", categoryService.getAll());
            return "product/create";
        }
        productService.updateImage(newProduct, imageProduct);
        if (categoryId != null && categoryId > 0) {
            Category selectedCategory = categoryService.get(categoryId);
            newProduct.setCategory(selectedCategory);
        }
        productService.add(newProduct);
        return "redirect:/products";
    }

    @GetMapping("/products/edit/{id}")
    public String Edit(@PathVariable int id, Model model) {
        Product find = productService.get(id);
        if (find == null) {
            return "error/404"; // Trang lỗi tùy chỉnh
        }
        model.addAttribute("product", find);
        model.addAttribute("categories", categoryService.getAll());
        return "product/edit";
    }

    @GetMapping("/products/delete/{id}")
    public String Delete(@PathVariable int id) {
        productService.delete(id);
        return "redirect:/products";
    }

    @PostMapping("/products/edit")
    public String Edit(@Valid Product editProduct,
                       BindingResult result,
                       @RequestParam(name = "categoryId", required = false) Integer categoryId,
                       @RequestParam("imageProduct") MultipartFile imageProduct,
                       Model model) {
        if (result.hasErrors()) {
            model.addAttribute("product", editProduct);
            model.addAttribute("categories", categoryService.getAll());
            return "product/edit";
        }
        if (categoryId != null && categoryId > 0) {
            Category selectedCategory = categoryService.get(categoryId);
            editProduct.setCategory(selectedCategory);
        } else {
            editProduct.setCategory(null);
        }
        if (imageProduct != null && !imageProduct.isEmpty()) {
            productService.updateImage(editProduct, imageProduct);
        }
        productService.update(editProduct);
        return "redirect:/products";
    }
}

