package com.shareit.service;

import com.shareit.entities.Role;
import com.shareit.enums.RoleType;

public interface RoleService {

    Role createNewRole(RoleType roleType);
    Role findByRoleName(RoleType roleType);

    void deleteRole(RoleType roleType);
}
