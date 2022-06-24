package com.shareit.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table( name = "comment" )
@Data
@EqualsAndHashCode
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @Column( name = "text" ,nullable = false)
    private String text;

    @Column( name = "comment_time" )
    @CreationTimestamp
    private Timestamp commentTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn( name = "user_id" )
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn( name = "post_id" )
    private Post post;

}
