package com.shareit.service;

import com.shareit.dto.projection.CommunityUserProjection;
import com.shareit.entities.Community;

import java.util.List;

public interface CommunityService {

    Community createCommunity(Community community);

    Community getCommunity(Long communityId);

    Community updateCommunity(Community community);

    void deleteCommunity(Community community);

    List<CommunityUserProjection> getUsersFromCommunity(Long communityId);
}
