package com.project.ottshare.controller;

import com.project.ottshare.dto.ottShareRoom.OttShareRoomIdAndPasswordResponse;
import com.project.ottshare.dto.ottShareRoomDto.OttShareRoomResponse;
import com.project.ottshare.dto.sharingUserDto.SharingUserResponse;
import com.project.ottshare.service.OttShareRoomService;
import com.project.ottshare.service.SharingUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/ottShareRooms")
@Slf4j
public class OttShareRoomApiController {

    private final OttShareRoomService ottShareRoomService;
    private final SharingUserService sharingUserService;

    /**
     * 채팅방
     */
    @GetMapping("/{userId}")
    public ResponseEntity<OttShareRoomResponse> getRoomDetails(@PathVariable("userId") Long userId) {
        log.info("Fetching OTT share room for user ID: {}", userId);
        SharingUserResponse sharingUser = sharingUserService.getSharingUserByUserId(userId);
        OttShareRoomResponse ottShareRoom = ottShareRoomService.getOttShareRoom(sharingUser.getOttShareRoom().getId());

        return ResponseEntity.ok(ottShareRoom);
    }

    /**
     * 강제퇴장
     */
    @DeleteMapping("/{roomId}/users/{userId}")
    public ResponseEntity<Void> kickUserFromRoom(@PathVariable("roomId") Long roomId,
                                                 @PathVariable("userId") Long userId) {
        log.info("Kicking user ID: {} from room ID: {}", userId, roomId);
        ottShareRoomService.expelUserFromRoom(roomId, userId);

        return ResponseEntity.ok().build();
    }

    /**
     * 스스로 채팅방 나가기
     */
    @DeleteMapping("/{roomId}/users/{userId}/leave")
    public ResponseEntity<Void> leaveRoom(@PathVariable("roomId") Long roomId,
                                          @PathVariable("userId") Long userId) {
        log.info("User ID: {} is leaving room ID: {}", userId, roomId);
        SharingUserResponse sharingUser = sharingUserService.getSharingUser(userId);
        //나가는 사람이 리더면 공유방 제거
        if (sharingUser.isLeader()) {
            ottShareRoomService.deleteOttShareRoom(roomId);
        } else {
            ottShareRoomService.leaveRoom(roomId, userId);
        }
        return ResponseEntity.ok().build();
    }

    /**
     * 체크
     */
    @PostMapping("/{roomId}/users/{userId}/check")
    public ResponseEntity<Void> checkUserInRoom(@PathVariable("roomId") Long roomId, @PathVariable("userId") Long userId) {
        log.info("Checking user ID: {} in room ID: {}", userId, roomId);
        ottShareRoomService.checkUserInRoom(roomId, userId);

        return ResponseEntity.ok().build();
    }

    /**
     * 아이디, 비밀번호 확인
     */
    @GetMapping("/{userId}/id-password")
    public ResponseEntity<OttShareRoomIdAndPasswordResponse> getRoomIdAndPassword(@PathVariable("userId") Long userId) {
        log.info("Fetching ID and Password for user ID: {}", userId);
        SharingUserResponse sharingUser = sharingUserService.getSharingUserByUserId(userId);
        Long roomId = sharingUser.getOttShareRoom().getId();
        OttShareRoomIdAndPasswordResponse ottShareRoomIdAndPasswordResponse = ottShareRoomService.getRoomIdAndPassword(userId, roomId);

        return ResponseEntity.ok(ottShareRoomIdAndPasswordResponse);
    }

    /**
     * 새로운 맴버 찾기
     */
    @GetMapping("/{roomId}/new-member")
    public ResponseEntity<String> findNewMember(@PathVariable("roomId") Long roomId) {
        log.info("Finding new member for room ID: {}", roomId);
        boolean newMemberFound = ottShareRoomService.findNewMember(roomId);
        String message = newMemberFound ? "새로운 멤버를 찾았습니다" : "새로운 멤버를 찾지 못 했습니다";

        return ResponseEntity.ok(message);
    }

}
