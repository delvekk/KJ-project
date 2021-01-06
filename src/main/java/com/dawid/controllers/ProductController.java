package com.dawid.controllers;

import com.dawid.commands.ProductCommand;
import com.dawid.commands.ReviewCommand;
import com.dawid.commands.UserCommand;
import com.dawid.domain.Cart;
import com.dawid.domain.Product;
import com.dawid.domain.User;
import com.dawid.exceptions.NotFoundException;
import com.dawid.services.CategoryService;
import com.dawid.services.ProductService;
import com.dawid.services.ReviewService;
import com.dawid.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;


@Controller
@Slf4j
public class ProductController {

    @Autowired
    private Cart cart;
    private final ProductService productService;
    private final ReviewService reviewService;
    private final UserService userService;
    private final CategoryService categoryService;


    public ProductController(ProductService productService, ReviewService reviewService, UserService userService, CategoryService categoryService) {
        this.productService = productService;
        this.reviewService = reviewService;
        this.userService = userService;
        this.categoryService = categoryService;
    }

    @GetMapping("/product/{id}/show")
    public String getProductById(@PathVariable String id, Model model, Principal principal, HttpServletRequest request){
        if (principal != null) {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            model.addAttribute("user", user);
        }
        ProductCommand productCommand = productService.getProductCommandById(Long.valueOf(id));
        ReviewCommand reviewCommand = new ReviewCommand();
        reviewCommand.setProductId(productCommand.getId());
        if (!model.containsAttribute("newReview")) {
            model.addAttribute("newReview", reviewCommand);
        }
        model.addAttribute("relatedProducts", productService.relatedProducts(productCommand));
        model.addAttribute("cart", cart);
        model.addAttribute("product", productCommand);

        return "product/show";
    }



    @GetMapping("/products")
    public String getProducts(Model model) {
        model.addAttribute("products", productService.getProducts());
        model.addAttribute("cart", cart);

        return "products/showall";
    }

    @PostMapping("/addreview")
    public String addReview(@ModelAttribute("newReview") @Valid ReviewCommand newReview, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.newReview", bindingResult);
            redirectAttributes.addFlashAttribute("newReview", newReview);
            return "redirect:/product/" + newReview.getProductId() + "/show";
        }
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserCommand userCommand = userService.findUserCommandByEmail(user.getEmail());
        newReview.setUser(userCommand);
        Product product = productService.getProductById(newReview.getProductId());
        reviewService.saveReview(newReview, product);

        return "redirect:/product/" + newReview.getProductId() + "/show";
    }

    @PostMapping("/deletereview")
    public String deleteReview(@ModelAttribute("review") ReviewCommand reviewCommand) {
        reviewService.deleteReview(Long.valueOf(reviewCommand.getId()));

        return "redirect:/product/" + reviewCommand.getProductId() + "/show";
    }


    @GetMapping("/search")
    public String searchProducts(Model model, @RequestParam("phrase") String phrase){
        model.addAttribute("products", productService.searchedProducts(phrase));
        model.addAttribute("cart", cart);

        return "products/showall";
    }

    @GetMapping("/admin/product/{id}/delete")
    public String deleteProduct(@PathVariable String id){
        productService.deleteProductById(Long.valueOf(id));
        return "redirect:/admin/showproducts";

    }

    @GetMapping("/admin/product/{id}/update")
    public String updateProduct(@PathVariable String id, Model model){
        ProductCommand productCommand = productService.getProductCommandById(Long.valueOf(id));
        model.addAttribute("product", productCommand);

        return "product/add";

    }


    @GetMapping("/admin/newproduct")
    public String showAddProduct(Model model){
        model.addAttribute("product", new ProductCommand());
        model.addAttribute("allcategories", categoryService.getCategoryCommands());

        return "product/add";


    }

    @PostMapping("/admin/newproduct")
    public String addProduct(@ModelAttribute("product") @Valid ProductCommand productCommand, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "product/add";
        }

        productService.saveProductCommand(productCommand);

        return "redirect:/admin/showproducts";

    }

    @GetMapping("/admin/showproducts")
    public String showProducts(Model model){
        model.addAttribute("products", productService.getProducts());

        return "product/showall";
    }



    @ExceptionHandler(NotFoundException.class)
    public String handleNotFund(RedirectAttributes redirectAttributes){
        redirectAttributes.addFlashAttribute("products", productService.getProducts());
        redirectAttributes.addFlashAttribute("cart", cart);
        redirectAttributes.addFlashAttribute("exception", "Nie znaleziono produktu");

        return "redirect:/products";

    }





}
