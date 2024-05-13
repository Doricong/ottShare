package com.project.ottshare.dto.ottShareRoomDto;

import com.project.ottshare.dto.sharingUserDto.OttRoomMemberResponse;
import com.project.ottshare.entity.Message;
import com.project.ottshare.entity.OttShareRoom;
import com.project.ottshare.entity.SharingUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MessageRequest {

    private OttShareRoomResponse ottShareRoom;

    private OttRoomMemberResponse ottRoomMemberResponse;

    private String message;

    public Message toEntity() {
        OttShareRoom roomEntity = ottShareRoom.toEntity(); // DTO를 엔터티로 변환
        SharingUser userEntity = ottRoomMemberResponse.toEntity();

        return Message.builder()
                .ottShareRoom(roomEntity)
                .sharingUser(userEntity)
                .message(message)
                .build();
    }
}
