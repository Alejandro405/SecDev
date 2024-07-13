package com.example.secdev.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.math.BigInteger;
import java.util.List;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Role {

    @Id
    private BigInteger id;

    private String name;

    private String description;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> authorities;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users;
}
