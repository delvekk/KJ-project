package com.dawid.controllers;

import com.dawid.commands.UserCommand;
import com.dawid.domain.User;
import com.dawid.services.EmailService;
import com.dawid.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.util.UUID;

@Controller
@Slf4j
public class RegisterController {

    private final UserService userService;
    private final EmailService emailService;

    public RegisterController(UserService userService, EmailService emailService) {
        this.userService = userService;
        this.emailService = emailService;
    }

    @GetMapping("/register")
    public String register(Model model, Principal principal) {
        model.addAttribute("user", new UserCommand());

        return principal == null ? "registerform" : "redirect:/";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("user") @Valid UserCommand userCommand, BindingResult bindingResult, HttpServletRequest request, Model model) {
        User userExists = userService.findUserByEmail(userCommand.getEmail());
        if (userExists != null) {
            bindingResult.rejectValue("email", "error.user", "Użytkownik z tym emailem już istnieje");
        }
        if (bindingResult.hasErrors()) {
            return "registerform";
        } else {
            model.addAttribute("activateInfo", "Wysłano link aktywacyjny na " + userCommand.getEmail() + ".");
            userCommand.setConfirmationToken(UUID.randomUUID().toString());

            String appURL = request.getScheme() + "://" + request.getServerName();
            emailService.sendEmail(userCommand.getEmail(), "Potwierdzenie rejestracji",
                    "By potwierdzić Twój email, proszę kliknąć na poniższy adres\n"
                            + appURL + "/confirm?token=" + userCommand.getConfirmationToken());
            userService.saveUserCommand(userCommand);
        }

        return "registerform";
    }


    @GetMapping("/confirm")
    public String showConfirmationPage(Model model, @RequestParam("token") String token) {
        User user = userService.findByConfirmationToken(token);
        if (user == null) {
            model.addAttribute("failure", "Coś poszło nie tak");
        } else {
            if(user.getActive() == 1){
                return "redirect:/";
            }
            model.addAttribute("confirmation", "Konto aktywowane. Możesz się zalogować");
            userService.activateAccount(user);
        }
        return "loginform";
    }
}
