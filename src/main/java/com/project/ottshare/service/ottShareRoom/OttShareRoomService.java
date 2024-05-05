package com.project.ottshare.service.ottShareRoom;

import com.project.ottshare.dto.ottShareRoom.OttSharingRoomRequest;
import com.project.ottshare.dto.waitingUserDto.WaitingUserResponse;
import com.project.ottshare.entity.OttShareRoom;

import java.util.List;

public interface OttShareRoomService {

    OttShareRoom save(OttSharingRoomRequest ottSharingRoomRequest);

    void delete(Long id);

    void expelUser(Long roomId, Long userId);

    void checkUser(Long roomId, Long userId);
}
