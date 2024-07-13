package com.example.secdev.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "user_service")
public class User implements UserDetails {
    // Definir el ide como numerado, autoiuncremental
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String lastName;

    @NotNull
    @NotBlank
    @Column(name = "email", unique = true)
    private String email;

    @NotNull
    @NotBlank
    private String password;

    @ManyToMany(
            fetch = FetchType.EAGER,
            cascade = {
                CascadeType.PERSIST,
                CascadeType.MERGE
    })
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;

    public User(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.roles = user.getRoles();
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> res = new HashSet<>();

        for (Role role : roles) {
            res.add(new SimpleGrantedAuthority(role.getName()));
        }

        return res;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
