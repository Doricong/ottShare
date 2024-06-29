package com.project.ottshare.service.ottShareRoom;

import com.project.ottshare.dto.ottShareRoom.OttShareRoomIdAndPasswordResponse;
import com.project.ottshare.dto.ottShareRoomDto.OttShareRoomResponse;
import com.project.ottshare.dto.ottShareRoomDto.OttSharingRoomRequest;

public interface OttShareRoomService {

    Long createOttShareRoom(OttSharingRoomRequest ottSharingRoomRequest);

    OttShareRoomResponse getOttShareRoom(Long id);

    void deleteOttShareRoom(Long id);

    void expelUserFromRoom(Long roomId, Long userId);

    void leaveRoom(Long roomId, Long userId);

    void checkUserInRoom(Long roomId, Long userId);

    OttShareRoomIdAndPasswordResponse getRoomIdAndPassword(Long roomId, Long userId);

    boolean findNewMember(Long roomId);
}
