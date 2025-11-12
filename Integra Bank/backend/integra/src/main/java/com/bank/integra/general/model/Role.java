package com.bank.integra.general.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="roles")
@Data
public class Role {
    @Id
    @Column(name="user_id")
    private Integer id;

    @Column(name="role")
    private String role;

    public Role() {

    }

    public Role(Integer id, String role) {
        this.id = id;
        this.role = role;
    }
}
