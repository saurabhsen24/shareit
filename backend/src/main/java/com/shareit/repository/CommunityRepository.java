package com.shareit.repository;

import com.shareit.dto.projection.CommunityUserProjection;
import com.shareit.entities.Community;
import com.shareit.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface CommunityRepository extends JpaRepository<Community, UUID> {

    @Query(value = "SELECT new com.shareit.dto.projection.CommunityUserProjection(u.user_id,u.user_name,u.email,u.profile_pic) " +
            "FROM community c JOIN user_community uc ON c.community_id = uc.community_id " +
            "JOIN user u ON uc.user_id = u.user_id WHERE c.community_id=:communityId",nativeQuery = true)
    List<CommunityUserProjection> getAllUsersFromCommunity(@Param("communityId") String communityId);

    @Query(value = "SELECT COUNT(*) FROM " +
            "community c JOIN user_community uc ON c.community_id = uc.community_id " +
            "JOIN user u ON uc.user_id = u.user_id WHERE c.community_id=:communityId",nativeQuery = true)
    Integer getCommunitySize(@Param("communityId") String communityId);

}
