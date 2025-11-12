package com.bank.integra.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminDashController {

    @GetMapping("/")
    public String redirectToMain() {
        return "redirect:/admin/home";
    }

    @GetMapping("/home")
    public String showMainAdminPage() {
        return "adminDash";
    }
}
