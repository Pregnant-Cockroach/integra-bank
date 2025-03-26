package com.bank.integra.dao;

import com.bank.integra.entities.person.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findUserById(Integer id);
}
