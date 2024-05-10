package com.project.ottshare.dto.sharingUserDto;

import com.project.ottshare.dto.ottShareRoomDto.OttShareRoomResponse;
import com.project.ottshare.dto.userDto.UserResponse;
import com.project.ottshare.entity.OttShareRoom;
import com.project.ottshare.entity.SharingUser;
import com.project.ottshare.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SharingUserResponse {

    private Long id;

    private UserResponse user;

    private OttShareRoomResponse ottShareRoom;

    private boolean isLeader;

    private boolean isChecked;

    public SharingUserResponse(SharingUser sharingUser) {
        this.id = sharingUser.getId();
        this.user = new UserResponse(sharingUser.getUser());  // User 엔티티를 UserResponse DTO로 변환
        this.ottShareRoom = new OttShareRoomResponse(sharingUser.getOttShareRoom());
        this.isLeader = sharingUser.isLeader();
        this.isChecked = sharingUser.isChecked();
    }

    public SharingUser toEntity() {
        SharingUser sharingUser = SharingUser.builder()
                .id(getId())
                .user(user.toEntity())  // UserResponse DTO에서 User 엔티티로 변환
                .ottShareRoom(ottShareRoom.toEntity())
                .isLeader(isLeader)
                .isChecked(isChecked)
                .build();

        return sharingUser;
    }
}
