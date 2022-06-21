package com.shareit.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Entity
@Table( name = "user" )
@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue
    private UUID userId;

    @Column( name = "user", nullable = false, unique = true )
    private String userName;

    @Column( name = "password", nullable = false )
    private String password;

    @Column( name = "email" )
    private String email;

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
    @JsonIgnoreProperties( value = "user" )
    private List<Post> posts;

    @OneToMany( mappedBy = "user" )
    @JsonIgnoreProperties( value = "user" )
    private List<Comment> comments;

    @OneToMany( mappedBy = "user")
    @JsonIgnoreProperties( value = "user" )
    private List<Vote> votes;

    @OneToMany(mappedBy = "user")
    private List<UserCommunity> communityOfUsers;

}
