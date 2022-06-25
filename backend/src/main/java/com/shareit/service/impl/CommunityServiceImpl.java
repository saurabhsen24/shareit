package com.shareit.service.impl;

import com.shareit.dto.projection.CommunityUserProjection;
import com.shareit.entities.Community;
import com.shareit.exception.ResourceNotFoundException;
import com.shareit.repository.CommunityRepository;
import com.shareit.service.CommunityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommunityServiceImpl implements CommunityService {

    @Autowired
    private CommunityRepository communityRepository;

    @Override
    public Community createCommunity(Community community) {
        return communityRepository.save(community);
    }

    @Override
    public Community getCommunity(Long communityId) {
        return communityRepository.findById(communityId).orElseThrow(() -> new ResourceNotFoundException("Community Not Found!"));
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
    public List<CommunityUserProjection> getUsersFromCommunity(Long communityId) {
        return communityRepository.getAllUsersFromCommunity(communityId);
    }
}
