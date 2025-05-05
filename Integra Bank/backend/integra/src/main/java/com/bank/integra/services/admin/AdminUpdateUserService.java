package com.bank.integra.services.admin;

import com.bank.integra.dao.UserDetailsRepository;
import com.bank.integra.entities.details.UserDetails;
import com.bank.integra.entities.person.User;
import com.bank.integra.services.DTO.AdminDTO;
import com.bank.integra.services.person.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Service
public class AdminUpdateUserService {
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public void updateUserFromForm(Integer id, AdminDTO adminDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        System.out.println("oleg:" + adminDTO.getPassword() + "|");
        User user = userService.getUserById(adminDTO.getUserId());
        if(!adminDTO.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(adminDTO.getPassword()));
        }
        UserDetails userDetails = userService.getUserDetailsByUserId(adminDTO.getUserId());
        if(adminDTO.getBalance() != null) {
            userDetails.setBalance(adminDTO.getBalance());
        }
        userDetails.setFirstName(adminDTO.getFirstName());
        userDetails.setLastName(adminDTO.getLastName());
        userDetails.setEmail(adminDTO.getEmail());
        user.setUserDetails(userDetails);
        if (user == null) {
            throw new RuntimeException("User " + id + " not found.");
        }
        userService.updateUser(user);
        redirectAttributes.addFlashAttribute("update", true);
    }
}
