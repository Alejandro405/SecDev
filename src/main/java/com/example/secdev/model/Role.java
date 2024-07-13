package com.example.secdev.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.math.BigInteger;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Role {

    @Id
    private BigInteger id;

    private String name;

    private String description;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> authorities;

    @ManyToMany(mappedBy = "roles")
    private List<User> users;
}
