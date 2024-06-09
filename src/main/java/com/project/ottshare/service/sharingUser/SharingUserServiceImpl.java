package com.project.ottshare.service.sharingUser;

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

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
@Slf4j
public class SharingUserServiceImpl implements SharingUserService{

    private final SharingUserRepository sharingUserRepository;

    @Override
    @Transactional
    public List<SharingUser> prepareSharingUsers(List<WaitingUserResponse> responses) {
        List<SharingUser> sharingUser = new ArrayList<>();
        for (WaitingUserResponse member : responses) {
            SharingUser entity = member.toEntity();
            sharingUser.add(entity);
            entity.getUser().checkShareRoom();
        }
        return sharingUser;
    }

    @Override
    @Transactional
    public void associateRoomWithSharingUsers(List<SharingUser> sharingUsers, OttShareRoomResponse room) {
        for (SharingUser sharingUser : sharingUsers) {
            OttShareRoom entity = room.toEntity();
            sharingUser.addRoom(entity);
        }
    }

    @Override
    public SharingUserResponse getSharingUserByUserId(Long userId) {
        SharingUser sharingUser = sharingUserRepository.findByUserUserId(userId)
                .orElseThrow(() -> new SharingUserNotFoundException(userId));

        SharingUserResponse sharingUserResponse = new SharingUserResponse(sharingUser);

        return sharingUserResponse;
    }

    @Override
    public SharingUserResponse getSharingUser(Long userId) {
        SharingUser sharingUser = sharingUserRepository.findById(userId)
                .orElseThrow(() -> new SharingUserNotFoundException(userId));

        SharingUserResponse sharingUserResponse = new SharingUserResponse(sharingUser);

        return sharingUserResponse;
    }

    public Optional<IsLeaderAndOttResponse> getSharingUserIsLeaderAndOttByUserId(Long userId) {
        Optional<SharingUser> sharingUser = sharingUserRepository.findByUserUserId(userId);
        return sharingUser.map(user -> new IsLeaderAndOttResponse(user.isLeader(), user.getOttShareRoom().getOtt()));
    }

}
