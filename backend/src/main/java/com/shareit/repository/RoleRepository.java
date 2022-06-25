package com.shareit.repository;

import com.shareit.entities.Role;
import com.shareit.enums.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByRoleType(RoleType roleType);

}
