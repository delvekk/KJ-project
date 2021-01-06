package com.dawid.controllers;


import com.dawid.commands.OrderCommand;
import com.dawid.commands.UserCommand;
import com.dawid.domain.Cart;
import com.dawid.domain.User;
import com.dawid.services.OrderService;
import com.dawid.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

@Controller
@Slf4j
public class AccountController {

    @Autowired
    private Cart cart;
    private final UserService userService;
    private final OrderService orderService;

    public AccountController(UserService userService, OrderService orderService) {
        this.userService = userService;
        this.orderService = orderService;
    }

    @GetMapping("/user/account")
    public String aboutUser(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("user", userService.findUserByEmail(user.getEmail()));
        model.addAttribute("cart", cart);

        return "user/account";


    }

    @PostMapping("/update")
    public String update(@ModelAttribute("user") @Valid UserCommand userCommand, BindingResult bindingResult, Model model) {
        model.addAttribute("cart", cart);

        if (bindingResult.hasErrors()) {
            return "user/account";
        }
        userService.updateUser(userCommand);
        return "redirect:/user/account";
    }

    @GetMapping("/user/orders")
    public String userOrderHistory(Model model) {
        model.addAttribute(cart);
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Set<OrderCommand> orders = new TreeSet<>(Comparator.comparing(OrderCommand::getDate).reversed());
        orderService.getUserOrders(user.getId()).forEach(o -> orders.add(o));
        model.addAttribute("orders", orders);

        return "user/orders";
    }
}
