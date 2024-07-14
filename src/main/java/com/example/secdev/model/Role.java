package com.example.secdev.model;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import java.math.BigInteger;
import java.util.List;
import java.util.Set;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Role {

    @Id
    private BigInteger id;

    private String name;

    private String description;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users;

    public Role(String desciption, String name, Set<User> users) {
        this.description = desciption;
        this.name = name;
        this.users = users;
    }
}
