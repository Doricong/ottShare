package com.project.ottshare.service;

import com.project.ottshare.dto.ottShareRoom.OttShareRoomIdAndPasswordResponse;
import com.project.ottshare.dto.ottShareRoomDto.OttShareRoomResponse;
import com.project.ottshare.dto.ottShareRoomDto.OttSharingRoomRequest;
import com.project.ottshare.entity.OttShareRoom;
import com.project.ottshare.entity.SharingUser;
import com.project.ottshare.entity.User;
import com.project.ottshare.entity.WaitingUser;
import com.project.ottshare.enums.OttType;
import com.project.ottshare.exception.OttSharingRoomNotFoundException;
import com.project.ottshare.exception.SharingUserNotCheckedException;
import com.project.ottshare.exception.SharingUserNotFoundException;
import com.project.ottshare.repository.MessageRepository;
import com.project.ottshare.repository.OttShareRoomRepository;
import com.project.ottshare.repository.SharingUserRepository;
import com.project.ottshare.repository.WaitingUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class OttShareRoomService {

    private final OttShareRoomRepository ottShareRoomRepository;
    private final SharingUserRepository sharingUserRepository;
    private final WaitingUserRepository waitingUserRepository;
    private final MessageRepository messageRepository;
    private final SharingUserFactory sharingUserFactory;

    /**
     * ott 공유방 생성
     */
    @Transactional
    public Long createOttShareRoom(OttSharingRoomRequest ottSharingRoomRequests) {
        OttShareRoom entity = ottSharingRoomRequests.toEntity();
        OttShareRoom savedOttShareRoom = ottShareRoomRepository.save(entity);
        log.info("Saved OttShareRoom with ID: {}", savedOttShareRoom.getId());

        return savedOttShareRoom.getId();
    }

    public OttShareRoomResponse getOttShareRoom(Long id) {
        OttShareRoom ottShareRoom = ottShareRoomRepository.findById(id)
                .orElseThrow(() -> new OttSharingRoomNotFoundException(id));

        return new OttShareRoomResponse(ottShareRoom);
    }

    /**
     * ott 공유방 삭제
     */
    @Transactional
    public void deleteOttShareRoom(Long id) {
        OttShareRoom ottShareRoom = ottShareRoomRepository.findById(id)
                .orElseThrow(() -> new OttSharingRoomNotFoundException(id));
        // OttShareRoom에 연결된 모든 메시지 삭제
        messageRepository.deleteByOttShareRoomId(id);
        // OttShareRoom에 연결된 모든 사용자를 삭제
        sharingUserRepository.deleteByOttShareRoomId(id);

        ottShareRoom.getSharingUsers().stream()
                .map(SharingUser::getUser)
                .forEach(User::leaveShareRoom);

        ottShareRoomRepository.delete(ottShareRoom);
        log.info("Removed OttShareRoom with ID: {}", id);
    }

    /**
     * ott 공유방 강제퇴장
     */
    @Transactional
    public void expelUserFromRoom(Long roomId, Long userId) {
        SharingUser sharingUser = sharingUserRepository.findUserByRoomIdAndUserId(roomId, userId)
                .orElseThrow(() -> new OttSharingRoomNotFoundException("User not found in the room"));
        sharingUser.getUser().leaveShareRoom();
        OttShareRoom ottShareRoom = sharingUser.getOttShareRoom();
        //user 제거
        ottShareRoom.removeUser(sharingUser);
        sharingUserRepository.delete(sharingUser);
        log.info("Expelled user with ID: {} from room ID: {}", userId, roomId);
    }

    @Transactional
    public void leaveRoom(Long roomId, Long userId) {
        SharingUser sharingUser = sharingUserRepository.findUserByRoomIdAndUserId(roomId, userId)
                .orElseThrow(() -> new OttSharingRoomNotFoundException("User not found in the room"));
        sharingUser.getUser().leaveShareRoom();

        OttShareRoom ottShareRoom = sharingUser.getOttShareRoom();
        //user 제거
        ottShareRoom.removeUser(sharingUser);

        sharingUserRepository.delete(sharingUser);
        log.info("User with ID: {} left room ID: {}", userId, roomId);
    }

    /**
     * 체크 기능
     */
    @Transactional
    public void checkUserInRoom(Long roomId, Long userId) {
        SharingUser sharingUser = sharingUserRepository.findUserByRoomIdAndUserId(roomId, userId)
                .orElseThrow(() -> new SharingUserNotFoundException(userId));

        sharingUser.checked();
        log.info("Checked user with ID: {} in room ID: {}", userId, roomId);
    }

    /**
     * 아이디, 비밀번호 확인
     */
    public OttShareRoomIdAndPasswordResponse getRoomIdAndPassword(Long roomId, Long userId) {
        SharingUser sharingUser = sharingUserRepository.findUserByRoomIdAndUserId(roomId, userId)
                .orElseThrow(() -> new SharingUserNotFoundException(userId));

        OttShareRoom ottShareRoom = sharingUser.getOttShareRoom();

        if (!sharingUser.isChecked()) {
            throw new SharingUserNotCheckedException(userId);
        }

        return new OttShareRoomIdAndPasswordResponse(ottShareRoom.getOttId(), ottShareRoom.getOttPassword());
    }

    /**
     * 새로운 맴버 찾기
     */
    public boolean findNewMember(Long roomId) {
        // OttShareRoom의 ottType을 기준으로 대기 목록에서 새로운 멤버를 찾습니다.
        OttShareRoom ottShareRoom = ottShareRoomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Room not found"));

        OttType ottType = ottShareRoom.getOtt();
        List<WaitingUser> newMembers = waitingUserRepository.findNonLeadersByOtt(ottType, 1);

        if (!newMembers.isEmpty()) {
            WaitingUser waitingUser = newMembers.get(0);
            // 팩토리 서비스를 사용하여 SharingUser 생성
            SharingUser sharingUser = sharingUserFactory.createFromWaitingUser(waitingUser, ottShareRoom);
            // OttShareRoom에 SharingUser 추가
            ottShareRoom.addUser(sharingUser);
            waitingUserRepository.delete(waitingUser);  // 대기 목록에서 제거
            ottShareRoomRepository.save(ottShareRoom); // 방 업데이트
            log.info("Added new member to room ID: {}", roomId);
            return true;  // 멤버를 찾은 경우 true 반환
        }
        log.warn("No new members found for room ID: {}", roomId);
        return false;  // 멤버를 찾지 못한 경우 false 반환
    }

}
