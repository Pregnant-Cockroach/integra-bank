package com.bank.integra.controller.user;

import com.bank.integra.entities.details.UserDetails;
import com.bank.integra.entities.person.User;
import com.bank.integra.services.person.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DashREST {
    @Autowired
    UserService userService;

    @GetMapping("/{id}/details")
    public UserDetails showPrintDetails(@PathVariable Integer id) {
        return userService.getUserDetailsByUserId(id);
    }

    @GetMapping("/{id}")
    public User showPrint(@PathVariable Integer id) {
        return userService.getUserById(id);
    }
}
