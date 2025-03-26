package com.bank.integra.login;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminLoginController {
    @GetMapping("/staffLogin")
    public String showLoginPage() {
        return "integra_login2";
    }
}
