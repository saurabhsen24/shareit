package com.shareit.service;

import com.shareit.entities.User;

import java.util.Optional;

public interface UserService {

    User getUserByUsername(String userName);

    void saveUser(User user);

    User getUserById(Long userId);

    User updateUser(User user);

    void deleteUser(Long userId);
}
