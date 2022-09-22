package com.shareit.dto;

import com.shareit.entities.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {

    private String email;
    private String worksAt;
    private String college;
    private String homeTown;
    private String currentCity;
    private String countryName;
    private String gender;

    private String userProfilePic;

    public static UserDto from(User user) {
        return UserDto.builder()
                .email(user.getEmail())
                .worksAt(user.getWorksAt())
                .college(user.getCollege())
                .homeTown(user.getHomeTown())
                .currentCity(user.getCurrentCity())
                .countryName(user.getCountryName())
                .gender(user.getGender())
                .userProfilePic(user.getProfilePicUrl())
                .build();
    }
}
