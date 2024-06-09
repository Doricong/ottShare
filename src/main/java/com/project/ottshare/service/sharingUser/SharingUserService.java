package com.project.ottshare.service.sharingUser;

import com.project.ottshare.dto.ottShareRoomDto.OttShareRoomResponse;
import com.project.ottshare.dto.sharingUserDto.IsLeaderAndOttResponse;
import com.project.ottshare.dto.sharingUserDto.SharingUserResponse;
import com.project.ottshare.dto.waitingUserDto.WaitingUserResponse;
import com.project.ottshare.entity.SharingUser;

import java.util.List;
import java.util.Optional;

public interface SharingUserService {

    List<SharingUser> prepareSharingUsers(List<WaitingUserResponse> responses);

    void associateRoomWithSharingUsers(List<SharingUser> sharingUsers, OttShareRoomResponse room);

    SharingUserResponse getSharingUserByUserId(Long userId);

    SharingUserResponse getSharingUser(Long userId);

    Optional<IsLeaderAndOttResponse> getSharingUserIsLeaderAndOttByUserId(Long userId);
}
