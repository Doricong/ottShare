package com.project.ottshare.service.ottShareRoom;

import com.project.ottshare.dto.ottShareRoom.OttSharingRoomRequest;
import com.project.ottshare.dto.waitingUserDto.WaitingUserResponse;
import com.project.ottshare.entity.OttShareRoom;
import com.project.ottshare.entity.SharingUser;
import com.project.ottshare.entity.WaitingUser;
import com.project.ottshare.exception.OttSharingRoomNotFoundException;
import com.project.ottshare.exception.SharingUserNotFoundException;
import com.project.ottshare.exception.UserNotFoundException;
import com.project.ottshare.repository.OttShareRoomRepository;
import com.project.ottshare.repository.SharingUserRepository;
import com.project.ottshare.repository.WaitingUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class OttShareRoomServiceImpl implements OttShareRoomService{

    private final OttShareRoomRepository ottShareRoomRepository;
    private final SharingUserRepository sharingUserRepository;

    /**
     * ott 공유방 생성
     */
    @Override
    @Transactional
    public void save(OttSharingRoomRequest ottSharingRoomRequests) {
        OttShareRoom entity = ottSharingRoomRequests.toEntity(ottSharingRoomRequests);

        ottShareRoomRepository.save(entity);
    }

    /**
     * ott 공유방 삭제
     */
    @Override
    @Transactional
    public void delete(Long id) {
        OttShareRoom ottShareRoom = ottShareRoomRepository.findById(id)
                .orElseThrow(() -> new OttSharingRoomNotFoundException(id));

        ottShareRoomRepository.delete(ottShareRoom);
    }

    /**
     * ott 공유방 강제퇴장
     */
    @Override
    @Transactional
    public void expelUser(Long roomId,Long userId) {
        OttShareRoom ottShareRoom = ottShareRoomRepository.findById(roomId)
                .orElseThrow(() -> new OttSharingRoomNotFoundException(roomId));

        SharingUser sharingUser = sharingUserRepository.findSharingUserById(userId)
                .orElseThrow(() -> new OttSharingRoomNotFoundException("User not found in the room"));

        //user 제거
        ottShareRoom.removeUser(sharingUser);
        //todo: 대기방에 추가해야 함
    }

    @Override
    @Transactional
    public void checkUser(Long roomId, Long userId) {
        ottShareRoomRepository.findById(roomId)
                .orElseThrow(() -> new OttSharingRoomNotFoundException(roomId));

        SharingUser sharingUser = sharingUserRepository.findSharingUserById(userId)
                .orElseThrow(() -> new SharingUserNotFoundException(userId));

        sharingUser.checked();
    }


}
