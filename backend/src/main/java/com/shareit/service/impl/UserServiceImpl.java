package com.shareit.service.impl;

import com.shareit.entities.Role;
import com.shareit.entities.User;
import com.shareit.enums.RoleType;
import com.shareit.exception.BadRequestException;
import com.shareit.exception.NotFoundException;
import com.shareit.repository.UserRepository;
import com.shareit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User getUserByUsername(String userName) {
        return userRepository.findByUserName(userName).orElseThrow(() -> new BadRequestException("User not found"));
    }

    @Override
    public void saveUser(User user) {
        Role role = new Role(RoleType.USER);
        user.getRoles().add(role);
        userRepository.save(user);
    }

    @Override
    public User getUserById(String userId) {
        return userRepository.findById(UUID.fromString(userId)).orElseThrow(() -> new NotFoundException("User Not Found!"));
    }

    @Override
    public User updateUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(String userId) {
        userRepository.deleteById(UUID.fromString(userId));
    }

}
