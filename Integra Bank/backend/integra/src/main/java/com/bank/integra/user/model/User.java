package com.bank.integra.user.model;

import com.bank.integra.general.model.AbstractPerson;
import jakarta.persistence.*;

@Entity
@DiscriminatorValue("USER")
public class User extends AbstractPerson {
    public User() {

    }

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private UserDetails userDetails;

    public User(Integer id, String password, boolean active) {
        setId(id);
        setActive(active);
        setPassword(password);
    }

    public UserDetails getUserDetails() {
        return userDetails;
    }

    public void setUserDetails (UserDetails userDetails) {
        this.userDetails = userDetails;
    }
}
