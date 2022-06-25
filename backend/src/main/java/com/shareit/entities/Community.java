package com.shareit.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
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

    @Column( name = "community_name", unique = true )
    private String communityName;

    @Column( name = "description" )
    private String communityDescription;

    @Column( name = "created_on" )
    @CreationTimestamp
    private Timestamp createdOn;

    @OneToMany(mappedBy = "community", orphanRemoval = true)
    private List<UserCommunity> communityOfUsers;

    @OneToMany(mappedBy = "community",orphanRemoval = true)
    @JsonIgnoreProperties( value = "community" )
    private List<Post> posts;
}
