package com.project.ottshare.service.sharingUser;

import com.project.ottshare.dto.waitingUserDto.WaitingUserResponse;
import com.project.ottshare.entity.OttShareRoom;
import com.project.ottshare.entity.SharingUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class SharingUserServiceImpl implements SharingUserService{

    @Override
    @Transactional
    public List<SharingUser> prepareSharingUsers(List<WaitingUserResponse> responses) {
        List<SharingUser> sharingUser = new ArrayList<>();
        for (WaitingUserResponse member : responses) {
            SharingUser entity = member.toEntity();
            sharingUser.add(entity);
            entity.getUser().checkShareRoom();
        }
        return sharingUser;
    }

    @Override
    @Transactional
    public void associateRoomWithSharingUsers(List<SharingUser> sharingUsers, OttShareRoom room) {
        for (SharingUser sharingUser : sharingUsers) {
            sharingUser.addRoom(room);
        }
    }


}
