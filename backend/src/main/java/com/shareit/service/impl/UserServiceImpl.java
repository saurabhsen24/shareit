package com.shareit.service.impl;

import com.shareit.entities.Role;
import com.shareit.entities.User;
import com.shareit.enums.RoleType;
import com.shareit.exception.BadRequestException;
import com.shareit.exception.NotFoundException;
import com.shareit.repository.UserRepository;
import com.shareit.service.RoleService;
import com.shareit.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private RoleService roleService;
    @Autowired
    private UserRepository userRepository;

    @Override
    public User getUserByUsername(String userName) {
        return userRepository.findByUserName(userName).orElseThrow(() -> new BadRequestException("User not found"));
    }

    @Override
    public void saveUser(User user) {

        log.info("Saving the user {} in db", user.getUserName());

        Role role = roleService.findByRoleName(RoleType.USER);
        user.setRoles(Arrays.asList(role));
        userRepository.save(user);

        log.info("User {} saved in DB", user.getUserName());
    }

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User Not Found!"));
    }

    @Override
    public User updateUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

}
