package com.project.ottshare.service;

import com.project.ottshare.dto.ottShareRoomDto.OttShareRoomResponse;
import com.project.ottshare.dto.sharingUserDto.IsLeaderAndOttResponse;
import com.project.ottshare.dto.sharingUserDto.SharingUserResponse;
import com.project.ottshare.dto.waitingUserDto.WaitingUserResponse;
import com.project.ottshare.entity.OttShareRoom;
import com.project.ottshare.entity.SharingUser;
import com.project.ottshare.exception.SharingUserNotFoundException;
import com.project.ottshare.repository.SharingUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
@Slf4j
public class SharingUserService {

    private final SharingUserRepository sharingUserRepository;

    @Transactional
    public List<SharingUser> prepareSharingUsers(List<WaitingUserResponse> responses) {
        List<SharingUser> sharingUsers = responses.stream()
                .map(member -> {
                    SharingUser entity = member.toEntity();
                    entity.getUser().checkShareRoom();
                    return entity;
                })
                .collect(Collectors.toList());

        log.info("Prepared {} sharing users", sharingUsers.size());
        return sharingUsers;
    }

    @Transactional
    public void associateRoomWithSharingUsers(List<SharingUser> sharingUsers, OttShareRoomResponse room) {
        OttShareRoom entity = room.toEntity();
        sharingUsers.forEach(sharingUser -> sharingUser.addRoom(entity));

        log.info("Associated room ID {} with {} sharing users", room.getId(), sharingUsers.size());
    }

    public SharingUserResponse getSharingUserByUserId(Long userId) {
        SharingUser sharingUser = sharingUserRepository.findByUserUserId(userId)
                .orElseThrow(() -> {
                    log.error("SharingUser not found with userId: {}", userId);
                    return new SharingUserNotFoundException(userId);
                });

        return new SharingUserResponse(sharingUser);
    }

    public SharingUserResponse getSharingUser(Long userId) {
        SharingUser sharingUser = sharingUserRepository.findById(userId)
                .orElseThrow(() -> new SharingUserNotFoundException(userId));

        return new SharingUserResponse(sharingUser);
    }

    public Optional<IsLeaderAndOttResponse> getSharingUserIsLeaderAndOttByUserId(Long userId) {
        Optional<SharingUser> sharingUser = sharingUserRepository.findByUserUserId(userId);
        return sharingUser.map(user -> new IsLeaderAndOttResponse(user.isLeader(), user.getOttShareRoom().getOtt()));
    }

}
