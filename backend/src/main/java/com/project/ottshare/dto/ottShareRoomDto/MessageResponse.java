package com.project.ottshare.dto.ottShareRoomDto;

import com.project.ottshare.dto.sharingUserDto.OttRoomMemberResponse;
import com.project.ottshare.entity.Message;
import com.project.ottshare.entity.OttShareRoom;
import com.project.ottshare.entity.SharingUser;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class MessageResponse {

    private Long id;

    private OttShareRoomResponse ottShareRoom;

    private OttRoomMemberResponse ottRoomMemberResponse;

    private String message;

    public static MessageResponse from(Message message) {
        return MessageResponse.builder()
                .id(message.getId())
                .ottShareRoom(OttShareRoomResponse.from(message.getOttShareRoom()))
                .ottRoomMemberResponse(OttRoomMemberResponse.from(message.getSharingUser()))
                .message(message.getMessage())
                .build();
    }

}
