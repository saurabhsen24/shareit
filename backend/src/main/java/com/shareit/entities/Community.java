package com.shareit.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table( name = "community" )
@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Community {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long communityId;

    @Column( name = "description" )
    private String communityDescription;

    @OneToMany(mappedBy = "community", orphanRemoval = true)
    private List<UserCommunity> communityOfUsers;

    @OneToMany(mappedBy = "community",orphanRemoval = true)
    @JsonIgnoreProperties( value = "community" )
    private List<Post> posts;
}
