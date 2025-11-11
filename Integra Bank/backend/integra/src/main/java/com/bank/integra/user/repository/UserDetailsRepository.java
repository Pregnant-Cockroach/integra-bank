package com.bank.integra.user.repository;

import com.bank.integra.user.model.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDetailsRepository extends JpaRepository<UserDetails, Integer> {
    UserDetails findUserDetailsByUserId(Integer id);
    UserDetails findUserDetailsByEmail(String email);
    Boolean existsByUserId(Integer id);
    Boolean existsByEmail(String email);
}
