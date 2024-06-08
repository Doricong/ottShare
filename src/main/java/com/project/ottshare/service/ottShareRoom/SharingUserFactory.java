package com.project.ottshare.service.ottShareRoom;

import com.project.ottshare.entity.OttShareRoom;
import com.project.ottshare.entity.SharingUser;
import com.project.ottshare.entity.WaitingUser;
import org.springframework.stereotype.Service;

@Service
public class SharingUserFactory {

    public SharingUser createFromWaitingUser(WaitingUser waitingUser, OttShareRoom ottShareRoom) {
        return SharingUser.builder()
                .user(waitingUser.getUser())
                .isLeader(false)
                .isChecked(false)
                .ottShareRoom(ottShareRoom)
                .build();
    }
}