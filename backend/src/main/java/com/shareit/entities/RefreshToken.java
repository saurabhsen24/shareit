package com.shareit.entities;

import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table( name = "refresh_token" )
@Builder
@Data
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long refreshTokenId;

    @Column(name = "user_name",nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String token;

    @CreationTimestamp
    private Timestamp createdOn;
}
