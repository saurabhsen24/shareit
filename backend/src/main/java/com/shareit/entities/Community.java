package com.shareit.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table( name = "community" )
@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Community {

    @Id
    @GeneratedValue
    private UUID communityId;

    @Column( name = "description" )
    private String communityDescription;

    @OneToMany(mappedBy = "community")
    private List<UserCommunity> communityOfUsers;

    @OneToMany(mappedBy = "community",orphanRemoval = true)
    @JsonIgnoreProperties( value = "community" )
    private List<Post> posts;
}
