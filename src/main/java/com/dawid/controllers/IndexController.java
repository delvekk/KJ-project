package com.dawid.controllers;

import com.dawid.domain.Cart;
import com.dawid.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @Autowired
    private Cart cart;
    private final ProductService productService;


    public IndexController(ProductService productService) {
        this.productService = productService;
    }


    @GetMapping("/")
    public String getIndexItem(Model model) {

        model.addAttribute("discountProducts", productService.getByDiscount());
        model.addAttribute("newProducts", productService.getByNew());
        model.addAttribute("cart", cart);
        return "index";
    }

    @GetMapping("/adminindex")
    public String getAdminPanel(){
        return "admin/panel";
    }

    @GetMapping("/contact")
    public String getContact(Model model) {
        model.addAttribute("cart", cart);
        return "contact";
    }





}
