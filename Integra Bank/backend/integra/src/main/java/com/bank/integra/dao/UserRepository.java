package com.bank.integra.dao;

import com.bank.integra.entities.person.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findUserById(Integer id);
    List<User> findByDtype(String dtype);
}
