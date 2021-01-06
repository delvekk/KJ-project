package com.dawid.controllers;

import com.dawid.commands.OrderCommand;
import com.dawid.commands.ProductCommand;
import com.dawid.domain.Cart;
import com.dawid.domain.User;
import com.dawid.services.OrderService;
import com.dawid.services.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Map;

@Controller
@Slf4j
public class CartController {

    @Autowired
    private Cart cart;
    private final ProductService productService;
    private final OrderService orderService;

    public CartController(ProductService productService, OrderService orderService) {
        this.productService = productService;
        this.orderService = orderService;
    }

    @GetMapping("/showcart")
    public String showCart(Model model) {
        model.addAttribute("cart", cart);

        return "cart";
    }

    @GetMapping("/cart/product/{id}/add")
    public String addMoreToCart(@PathVariable String id) {
        ProductCommand updatedCommand = productService.getProductCommandById(Long.valueOf(id));
        if (cart.getProducts().containsKey(updatedCommand)) {
            cart.getProducts().put(updatedCommand, cart.getProducts().get(updatedCommand) + 1);
        } else {
            cart.getProducts().put(updatedCommand, 1);
        }
        return "redirect:/showcart";
    }

    @PostMapping("/addtocart")
    public String addProductToCart(@ModelAttribute("product") ProductCommand productCommand, HttpServletRequest request) {
        ProductCommand updatedCommand = productService.getProductCommandById(productCommand.getId());
        if (cart.getProducts().containsKey(updatedCommand)) {
            cart.getProducts().put(updatedCommand, cart.getProducts().get(updatedCommand) + 1);
        } else {
            cart.getProducts().put(updatedCommand, 1);
        }
        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }

    @GetMapping("/cart/product/{id}/remove")
    public String deleteProductFromCart(@PathVariable String id) {
        for (ProductCommand command : cart.getProducts().keySet()) {
            if (command.getId() == Long.valueOf(id)) {
                if (cart.getProducts().get(command) - 1 == 0) {
                    cart.getProducts().remove(command);
                    break;
                } else {
                    cart.getProducts().put(command, cart.getProducts().get(command) - 1);
                }
            }
        }
        return "redirect:/showcart";
    }

    @GetMapping("cart/removeall")
    public String deleteAllFromCart() {
        cart.getProducts().clear();

        return "redirect:/showcart";
    }

    @GetMapping("/cart/finalize")
    public String finalizeTransaction(Model model) {
        if(cart.countProducts() == 0){
            return "redirect:/showcart";
        }
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("user", user);
        model.addAttribute("cart", cart);
        OrderCommand orderCommand = new OrderCommand();
        orderCommand.setUserInfo(user);

        if(!model.containsAttribute("order")) {
            model.addAttribute("order", orderCommand);
        }
        return "finalize";
    }

    @PostMapping("/cart/finalize")
    public String finalizeTransaction(@ModelAttribute("order") @Valid OrderCommand orderCommand, BindingResult bindingResult, RedirectAttributes redirectAttributes){
        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.order", bindingResult);
            redirectAttributes.addFlashAttribute("order", orderCommand);
            return "redirect:/cart/finalize";
        }
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        for(Map.Entry<ProductCommand, Integer> command : cart.getProducts().entrySet()){
            for(int i = 0; i<command.getValue();i++){
                orderCommand.getProducts().add(command.getKey());
                productService.updateProductCommand(command.getKey());
            }
        }
        orderCommand.setUserInfo(user);
        orderService.saveOrderCommand(orderCommand);
        cart.getProducts().clear();

        return "redirect:/user/orders";
    }


}
