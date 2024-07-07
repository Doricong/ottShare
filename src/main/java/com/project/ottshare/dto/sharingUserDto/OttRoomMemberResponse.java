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

    public OttRoomMemberResponse(SharingUser sharingUser) {
        this.id = sharingUser.getId();
        this.user = new UserResponse(sharingUser.getUser());
        this.isLeader = sharingUser.isLeader();
        this.isChecked = sharingUser.isChecked();
    }
}
