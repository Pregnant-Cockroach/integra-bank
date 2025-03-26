package com.bank.integra.login;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class UserLoginController {

    @GetMapping("/login")
    public String showLoginPage() {
        return "integra_login1";
    }

}
