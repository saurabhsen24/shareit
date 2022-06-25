package com.shareit.repository;

import com.shareit.entities.UserCommunity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCommunityRepository extends JpaRepository<UserCommunity, Long> {
}
