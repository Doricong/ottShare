package com.project.ottshare.service.ottShareRoom;

import com.project.ottshare.dto.ottShareRoom.OttShareRoomIdAndPasswordResponse;
import com.project.ottshare.dto.ottShareRoomDto.OttShareRoomResponse;
import com.project.ottshare.dto.ottShareRoomDto.OttSharingRoomRequest;

public interface OttShareRoomService {

    Long save(OttSharingRoomRequest ottSharingRoomRequest);

    OttShareRoomResponse getOttShareRoom(Long id);

    void removeOttShareRoom(Long id);

    void expelUser(Long roomId, Long userId);

    void leaveRoom(Long roomId, Long userId);

    void checkUser(Long roomId, Long userId);

    OttShareRoomIdAndPasswordResponse idAndPassword(Long roomId, Long userId);
}
