package com.project.ottshare.dto.sharingUserDto;

import com.project.ottshare.dto.userDto.UserResponse;
import com.project.ottshare.entity.SharingUser;
import com.project.ottshare.entity.User;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OttRoomMemberResponse {

    private Long id;

    private UserResponse user;

    private boolean isLeader;

    private boolean isChecked;

    public static OttRoomMemberResponse from(SharingUser sharingUser) {
        return OttRoomMemberResponse.builder()
                .id(sharingUser.getId())
                .user(UserResponse.from(sharingUser.getUser()))
                .isLeader(sharingUser.isLeader())
                .isChecked(sharingUser.isChecked())
                .build();
    }
}
