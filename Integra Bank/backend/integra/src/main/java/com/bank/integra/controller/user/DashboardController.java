package com.bank.integra.controller.user;

import com.bank.integra.entities.details.UserDetails;
import com.bank.integra.services.person.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/user")
@Controller
public class DashboardController {
    @Autowired
    UserService userService;

    @GetMapping("/home")
    public String showMainPage(Authentication authentication, Model model) {
        Integer userId = Integer.parseInt(authentication.getName());
        UserDetails userDetails = userService.getUserDetailsByUserId(userId);
        model.addAttribute("user", userDetails);
        return "dashboard";
    }

    @GetMapping("/")
    public String showBase() {
        return "redirect:/home";
    }
}