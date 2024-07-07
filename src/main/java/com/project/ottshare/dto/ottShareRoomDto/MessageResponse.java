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

    public MessageResponse(Message message) {
        this.id = message.getId();
        this.ottShareRoom = new OttShareRoomResponse(message.getOttShareRoom());
        this.ottRoomMemberResponse = new OttRoomMemberResponse(message.getSharingUser());
        this.message = message.getMessage();
    }

}
