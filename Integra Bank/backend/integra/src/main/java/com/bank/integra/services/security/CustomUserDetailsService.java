package com.bank.integra.services.security;

import com.bank.integra.dao.AdminRepository;
import com.bank.integra.entities.person.Admin;
import com.bank.integra.entities.person.User;
import com.bank.integra.dao.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.stream.Collectors;

//TODO Нужен логгер для логов
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private HttpServletRequest request;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        try {
            Integer id = Integer.parseInt(userId);

            if(request.getRequestURI().contains("/staffAuthenticateTheUser")) {
                Admin admin = adminRepository.findById(id).orElse(null);
                if(admin == null) {
                    throw new UsernameNotFoundException("Admin not found with id: " + userId);
                }
                return createUserDetails(admin.getId(),admin.getPassword(),admin.getRoles());
            } else if (request.getRequestURI().contains("/authenticateTheUser")) {
                User user = userRepository.findById(id).orElse(null);
                if(user == null) {
                    throw new UsernameNotFoundException("User not found with id: " + userId);
                }
                return createUserDetails(user.getId(),user.getPassword(), user.getRoles());
            } else {
                return null;
            }
        } catch (NumberFormatException f) {
            throw new UsernameNotFoundException("Invalid user id: " + userId);
        }
    }

    private UserDetails createUserDetails(Integer id, String password, List<String> roles) {
        List<GrantedAuthority> authorities = roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toList());
        System.out.println("User: {"+id+"} has roles: {"+authorities+"}");
        return new org.springframework.security.core.userdetails.User(
                String.valueOf(id),
                password,
                authorities
        );
    }


}