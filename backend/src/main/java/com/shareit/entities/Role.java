package com.shareit.entities;

import com.shareit.enums.RoleType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table( name = "role" )
@Data
@EqualsAndHashCode
@NoArgsConstructor
public class Role {

    @Id
    @GeneratedValue
    private UUID roleId;

    @Column( name = "name" )
    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    @ManyToMany(mappedBy = "roles")
    private List<User> users;

    public Role(RoleType roleType){
        this.roleType = roleType;
    }
}
