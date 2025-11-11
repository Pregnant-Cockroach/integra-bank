package com.bank.integra.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/user")
@Controller
public class UserLoginController {

    @GetMapping("/login")
    public String showLoginPage() {
        return "integra_login1";
    }

}
