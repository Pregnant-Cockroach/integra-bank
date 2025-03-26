package com.bank.integra.entities.person;

import jakarta.persistence.*;

import java.util.List;

@MappedSuperclass
public abstract class AbstractPerson {
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    private List<String> roles;

    @Id
    @Column(name="user_id")
    private Integer id;

    @Column(name="pw")
    private String password;

    @Column(name="active")
    private boolean active;

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
