package com.project.ottshare.dto.sharingUserDto;

import com.project.ottshare.dto.ottShareRoomDto.OttShareRoomResponse;
import com.project.ottshare.dto.userDto.UserResponse;
import com.project.ottshare.entity.OttShareRoom;
import com.project.ottshare.entity.SharingUser;
import com.project.ottshare.entity.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SharingUserResponse {

    private Long id;

    private UserResponse user;

    private OttShareRoomResponse ottShareRoom;

    private boolean isLeader;

    private boolean isChecked;

    public static SharingUserResponse from(SharingUser sharingUser) {
        return SharingUserResponse.builder()
                .id(sharingUser.getId())
                .user(UserResponse.from(sharingUser.getUser()))
                .ottShareRoom(OttShareRoomResponse.from(sharingUser.getOttShareRoom()))
                .isLeader(sharingUser.isLeader())
                .isChecked(sharingUser.isChecked())
                .build();
    }
}
