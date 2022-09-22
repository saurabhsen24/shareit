package com.shareit.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.shareit.dto.UserDto;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table( name = "user" )
@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column( name = "user_name", nullable = false, unique = true )
    private String userName;

    @Column( name = "password", nullable = false )
    private String password;

    @Column( name = "email", nullable = false, unique = true )
    @Email
    private String email;

    @Column( name = "profile_pic" )
    private String profilePicUrl;

    @Column( name = "works_at" )
    private String worksAt;

    @Column( name = "college" )
    private String college;

    @Column( name = "home_town")
    private String homeTown;

    @Column( name = "current_city" )
    private String currentCity;

    @Column( name = "country_name" )
    private String countryName;

    @Column( name = "gender" )
    private String gender;

    @Column( name = "created_on" , nullable = false)
    @CreationTimestamp
    private Timestamp userCreatedOn;

    @Column( name = "modified_on", nullable = false )
    @UpdateTimestamp
    private Timestamp userUpdatedOn;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    @JsonIgnoreProperties(value = "users")
    private List<Role> roles;

    @OneToMany( mappedBy = "user", cascade = CascadeType.REMOVE )
    @JsonIgnoreProperties(value = "user")
    private List<Post> posts;

    @OneToMany( mappedBy = "user" )
    @JsonIgnoreProperties(value = "user")
    private List<Comment> comments;

    @OneToMany( mappedBy = "user")
    private List<Vote> votes;



    public static User from(UserDto userDto) {
        User user = User.builder()
                .worksAt(userDto.getWorksAt())
                .college(userDto.getCollege())
                .homeTown(userDto.getHomeTown())
                .currentCity(userDto.getCurrentCity())
                .countryName(userDto.getCountryName())
                .gender(userDto.getGender())
                .build();

        return user;
    }


    @Override
    public String toString() {
        return "User { userName =" + userName + " , password = " + password + " , email = "+ email + " }";
    }

}
