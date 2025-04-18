package com.bank.integra.controller.admin;

import com.bank.integra.entities.person.Admin;
import com.bank.integra.entities.person.User;
import com.bank.integra.services.person.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminDashController {


    //TODO Из-за того, что админ-юзер не имеет юзер детайлс всё идёт по пиздёнке.
    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public String showAllUsers(Model model) {
        List<User> users = userService.getAllUsers();
        System.out.println("Количество пользователей, полученных из сервиса: " + (users != null ? users.size() : "null"));
        if (users != null) {
            for (User user : users) {
                System.out.println("User ID: " + user.getId() + ", First Name: " + (user.getUserDetails() != null ? user.getUserDetails().getFirstName() : "No Details"));
            }
        }
        model.addAttribute("users", users);
        return "adminUsersList";
    }

    @GetMapping("/")
    public String redirectToMain() {
        return "redirect:/admin/home";
    }

    @GetMapping("/home")
    public String showMainAdminPage() {
        return "adminDash";
    }
}
