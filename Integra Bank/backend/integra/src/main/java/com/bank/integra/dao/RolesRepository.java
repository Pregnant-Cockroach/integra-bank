package com.bank.integra.dao;

import com.bank.integra.entities.role.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolesRepository extends JpaRepository<Role, Integer> {
}
