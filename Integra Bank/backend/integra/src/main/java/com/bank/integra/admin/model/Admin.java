package com.bank.integra.admin.model;

import com.bank.integra.general.model.AbstractPerson;
import jakarta.persistence.*;

@Entity
@Table(name = "members")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorValue("ADMIN")
public class Admin extends AbstractPerson {
    public Admin() {

    }

    public Admin(Integer id, String password, boolean active) {
        setId(id);
        setPassword(password);
        setActive(active);
    }
}
