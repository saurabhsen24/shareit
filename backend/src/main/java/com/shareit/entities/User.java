package com.shareit.entities;

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

    @Column( name = "email" )
    @Email
    private String email;

    @Column( name = "profile_pic" )
    private String profilePicUrl;

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
    private List<Role> roles;

    @OneToMany( mappedBy = "user" )
    private List<Post> posts;

    @OneToMany( mappedBy = "user" )
    private List<Comment> comments;

    @OneToMany( mappedBy = "user")
    private List<Vote> votes;

    @OneToMany(mappedBy = "user")
    private List<UserCommunity> communityOfUsers;

}
