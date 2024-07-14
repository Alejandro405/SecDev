package com.example.secdev.repo;

import aj.org.objectweb.asm.commons.InstructionAdapter;
import com.example.secdev.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;
import java.util.Optional;

public interface RolesRepo extends JpaRepository<Role, BigInteger> {

    Optional<Role> findByName(String role);
}
