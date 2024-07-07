package com.project.ottshare.dto.waitingUserDto;

import com.project.ottshare.entity.SharingUser;
import com.project.ottshare.entity.User;
import com.project.ottshare.entity.WaitingUser;
import com.project.ottshare.enums.OttType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WaitingUserResponse {

    private Long id;

    private User user;

    private OttType ott;

    private String ottId;

    private String ottPassword;

    private boolean isLeader;

    public WaitingUserResponse(WaitingUser waitingUser) {
        this.id = waitingUser.getId();
        this.user = waitingUser.getUser();
        this.ott = waitingUser.getOtt();
        this.ottId = waitingUser.getOttId();
        this.ottPassword = waitingUser.getOttPassword();
        this.isLeader = waitingUser.isLeader();
    }

}
