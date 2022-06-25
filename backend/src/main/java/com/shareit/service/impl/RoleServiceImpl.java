package com.shareit.service.impl;

import com.shareit.entities.Role;
import com.shareit.enums.RoleType;
import com.shareit.exception.BadRequestException;
import com.shareit.repository.RoleRepository;
import com.shareit.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role createNewRole(RoleType roleType) {
        Role role = new Role(roleType);
        return roleRepository.save(role);
    }

    @Override
    public Role findByRoleName(RoleType roleType) {

        log.info("Fetching role {} from db", roleType.name());
        return roleRepository.findByRoleType(roleType).orElse(null);
    }

    @Override
    public void deleteRole(RoleType roleType) {

        log.info("Deleting role {} from db", roleType.name());

        Role role = roleRepository.findByRoleType(roleType).orElseThrow(() -> new BadRequestException("Role not found"));
        roleRepository.delete(role);

        log.info("Role {} deleted from db", roleType.name());
    }
}
