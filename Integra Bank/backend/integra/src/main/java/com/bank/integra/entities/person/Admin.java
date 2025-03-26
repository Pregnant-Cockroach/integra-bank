package com.bank.integra.entities.person;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "members")
public class Admin extends AbstractPerson{
    public Admin() {

    }

    public Admin(Integer id, String password, boolean active) {
        setId(id);
        setPassword(password);
        setActive(active);
    }
}
