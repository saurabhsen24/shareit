package com.shareit.service.impl;

import com.shareit.dto.UserDto;
import com.shareit.dto.request.ChangePasswordRequest;
import com.shareit.entities.Role;
import com.shareit.entities.User;
import com.shareit.enums.RoleType;
import com.shareit.exception.BadRequestException;
import com.shareit.exception.ResourceNotFoundException;
import com.shareit.repository.UserRepository;
import com.shareit.service.FileUploadService;
import com.shareit.service.RoleService;
import com.shareit.service.UserService;
import com.shareit.utils.JwtHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private RoleService roleService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private FileUploadService fileUploadService;

    @Override
    public User getUserByUsername(String userName) {
        return userRepository.findByUserName(userName).orElseThrow(() -> new BadRequestException("User not found"));
    }

    @Override
    public Boolean checkIfUserExistsByUsernameOrEmail(String userName, String email) {
        return userRepository.existsByUserNameOrEmail(userName, email);
    }

    @Override
    public void saveUser(User user) {

        log.info("Saving the user {} in db", user.getUserName());

        List<Role> userRoles = new ArrayList<>();
        Role role = roleService.findByRoleName(RoleType.USER);

        if(Objects.isNull( role )) {
            throw new ResourceNotFoundException("Role not found");
        }

        userRoles.add(role);

        user.setRoles(userRoles);
        userRepository.save(user);

        log.info("User {} saved in DB", user.getUserName());
    }

    @Override
    public UserDto getUserDetails( String userName ) {
        User user = getUserByUsername(userName);
        return UserDto.from(user);
    }

    @Override
    public UserDto updateUser(UserDto userDto) {

        String currentUser = JwtHelper.getCurrentLoggedInUsername();

        userRepository.updateUserDetails(userDto.getWorksAt(), userDto.getCollege(), userDto.getHomeTown(),
                userDto.getCurrentCity(),userDto.getCountryName(), userDto.getGender(), currentUser);

        return userDto;
    }

    @Override
    public void deleteUser(Long userId) {
        if(BooleanUtils.isFalse(userRepository.existsById(userId))) {
            throw new ResourceNotFoundException("User not found");
        }

        userRepository.deleteById(userId);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void updatePassword(ChangePasswordRequest changePasswordRequest){
        User user = getUserByUsername(JwtHelper.getCurrentLoggedInUsername());

        if(passwordEncoder.matches(changePasswordRequest.getOldPassword(),user.getPassword())) {
            user.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
            saveUser(user);
        } else {
            throw new BadRequestException("Old Password is wrong");
        }
    }

    @Override
    public String uploadProfilePic(MultipartFile file) throws IOException {

        String userProfileImageUrl = fileUploadService.uploadFile(file);
        User user = getUserByUsername(JwtHelper.getCurrentLoggedInUsername());

        log.debug("Uploading User profile pic {} for user {}", file.getOriginalFilename(), user.getUserName());

        user.setProfilePicUrl( userProfileImageUrl );
        userRepository.save(user);

        log.debug("User profile pic uploaded successfully {}", userProfileImageUrl );

        return userProfileImageUrl;

    }


}
