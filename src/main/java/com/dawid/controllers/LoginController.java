package com.dawid.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;


@Controller
public class LoginController {


    @GetMapping("/login")
    public String login(Principal principal) {
        if (principal!= null) {
            return "redirect:/default";

        }
        return "loginform";
    }

    @GetMapping("/default")
    public String redirectToIndex(Principal principal){
        if(principal != null){
            return principal.getName().equals("delvekshop@gmail.com") ? "redirect:/adminindex" : "redirect:/";
        }
        return "redirect:/login";

    }



}
