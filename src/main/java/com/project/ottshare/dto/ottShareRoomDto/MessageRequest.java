package com.project.ottshare.dto.ottShareRoomDto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.ottshare.dto.sharingUserDto.OttRoomMemberResponse;
import com.project.ottshare.entity.Message;
import com.project.ottshare.entity.OttShareRoom;
import com.project.ottshare.entity.SharingUser;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MessageRequest {

    @NotNull(message = "OttShareRoom cannot be null")
    private OttShareRoomResponse ottShareRoom;

    @NotNull(message = "OttRoomMemberResponse cannot be null")
    private OttRoomMemberResponse ottRoomMemberResponse;

    @NotNull(message = "Message cannot be null")
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
