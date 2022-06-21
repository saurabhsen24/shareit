package com.shareit.service;

import com.shareit.dto.projection.CommunityUserProjection;
import com.shareit.entities.Community;
import com.shareit.entities.User;

import java.util.List;

public interface CommunityService {

    Community createCommunity(Community community);

    Community getCommunity(String communityId);

    Community updateCommunity(Community community);

    void deleteCommunity(Community community);

    List<CommunityUserProjection> getUsersFromCommunity(String communityId);
}
