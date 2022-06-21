package com.shareit.service.impl;

import com.shareit.dto.projection.CommunityUserProjection;
import com.shareit.entities.Community;
import com.shareit.entities.User;
import com.shareit.exception.NotFoundException;
import com.shareit.repository.CommunityRepository;
import com.shareit.service.CommunityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CommunityServiceImpl implements CommunityService {

    @Autowired
    private CommunityRepository communityRepository;

    @Override
    public Community createCommunity(Community community) {
        return communityRepository.save(community);
    }

    @Override
    public Community getCommunity(String communityId) {
        return communityRepository.findById(UUID.fromString(communityId)).orElseThrow(() -> new NotFoundException("Community Not Found!"));
    }

    @Override
    public Community updateCommunity(Community community) {
        return communityRepository.save(community);
    }

    @Override
    public void deleteCommunity(Community community) {
        communityRepository.delete(community);
    }

    @Override
    public List<CommunityUserProjection> getUsersFromCommunity(String communityId) {
        return communityRepository.getAllUsersFromCommunity(communityId);
    }
}
