package com.bank.integra.login;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class LoginRedirectController {
    @GetMapping("/redirect")
    public RedirectView redirectToDesiredPage() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        for(GrantedAuthority authority : authentication.getAuthorities()) {
            String role = authority.getAuthority();
            switch (role) {
                case "ROLE_ADMIN":
                    return new RedirectView("/admin/");
            }
        }
        return new RedirectView("/home");
    }
}
