package com.shareit.entities;

import com.shareit.enums.VoteType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table( name = "vote" )
@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long voteId;

    @Enumerated
    @Column( name = "vote_type" )
    private VoteType voteType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn( name = "user_id", nullable = false )
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn( name = "post_id", nullable = false )
    private Post post;
}
