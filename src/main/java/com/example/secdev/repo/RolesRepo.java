package com.example.secdev.repo;

import com.example.secdev.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;

public interface RolesRepo extends JpaRepository<Role, BigInteger> {

}
