package com.project.ottshare.dto.waitingUserDto;

import com.project.ottshare.entity.SharingUser;
import com.project.ottshare.entity.User;
import com.project.ottshare.entity.WaitingUser;
import com.project.ottshare.enums.OttType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class WaitingUserResponse {

    private Long id;

    private User user;

    private OttType ott;

    private boolean isLeader;

    public WaitingUserResponse(WaitingUser waitingUser) {
        this.id = waitingUser.getId();
        this.user = waitingUser.getUser();
        this.ott = waitingUser.getOtt();
        this.isLeader = waitingUser.isLeader();
    }

    public SharingUser toEntity() {
        SharingUser sharingUser = SharingUser.builder()
                .user(user)
                .isLeader(isLeader)
                .build();

        return sharingUser;
    }

}
