package com.dawid.controllers;


import com.dawid.services.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }



    @GetMapping("/admin/showorders")
    public String showAllOrders(Model model){
        model.addAttribute("orders", orderService.getOrders());

        return "orders/showall";
    }
}