package com.project.ottshare.service.sharingUser;

import com.project.ottshare.dto.ottShareRoomDto.OttShareRoomResponse;
import com.project.ottshare.dto.sharingUserDto.SharingUserResponse;
import com.project.ottshare.dto.waitingUserDto.WaitingUserResponse;
import com.project.ottshare.entity.SharingUser;

import java.util.List;

public interface SharingUserService {

    List<SharingUser> prepareSharingUsers(List<WaitingUserResponse> responses);

    void associateRoomWithSharingUsers(List<SharingUser> sharingUsers, OttShareRoomResponse room);

    SharingUserResponse getSharingUser(Long userId);
}
