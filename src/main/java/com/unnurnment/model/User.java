package com.unnurnment.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String email;
    private boolean Validation;

    @OneToMany(mappedBy = "user",cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    Set<Massage> massages = new HashSet<>();

    public Set<Massage> getMassages() {
        return massages;
    }

    public void setMassages(Set<Massage> massages) {
        this.massages = massages;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isValidation() {
        return Validation;
    }

    public void setValidation(boolean validation) {
        Validation = validation;
    }
}
