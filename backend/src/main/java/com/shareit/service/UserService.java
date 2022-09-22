package com.shareit.service;

import com.shareit.dto.UserDto;
import com.shareit.dto.request.ChangePasswordRequest;
import com.shareit.entities.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

public interface UserService {

    User getUserByUsername(String userName);

    Boolean checkIfUserExistsByUsernameOrEmail(String userName,String email);

    void saveUser(User user);

    UserDto getUserDetails( String userName );

    UserDto updateUser(UserDto user);

    void deleteUser(Long userId);

    Optional<User> findByEmail(String email);

    void updatePassword(ChangePasswordRequest changePasswordRequest);

    String uploadProfilePic(MultipartFile file) throws IOException;
}
