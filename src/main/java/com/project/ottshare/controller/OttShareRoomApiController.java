package com.project.ottshare.controller;

import com.project.ottshare.dto.ottShareRoom.OttShareRoomIdAndPasswordResponse;
import com.project.ottshare.dto.ottShareRoomDto.OttShareRoomResponse;
import com.project.ottshare.dto.sharingUserDto.SharingUserResponse;
import com.project.ottshare.security.auth.CustomUserDetails;
import com.project.ottshare.service.OttShareRoomService;
import com.project.ottshare.service.SharingUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ott-share-rooms")
@Slf4j
public class OttShareRoomApiController {

    private final OttShareRoomService ottShareRoomService;
    private final SharingUserService sharingUserService;

    /**
     * 채팅방
     */
    @GetMapping
    public ResponseEntity<OttShareRoomResponse> getRoomDetails(@AuthenticationPrincipal CustomUserDetails userDetails) {
        log.info("Fetching OTT share room for user ID: {}", userDetails.getId());
        SharingUserResponse sharingUser = sharingUserService.getSharingUserByUserId(userDetails.getId());
        OttShareRoomResponse ottShareRoom = ottShareRoomService.getOttShareRoom(sharingUser.getOttShareRoom().getId());

        return ResponseEntity.ok(ottShareRoom);
    }

    /**
     * 강제퇴장
     */
    @DeleteMapping("/{room-id}/users/{user-id}")
    public ResponseEntity<Void> kickUserFromRoom(@PathVariable("room-id") Long roomId,
                                                 @PathVariable("user-id") Long userId) {
        log.info("Kicking user ID: {} from room ID: {}", userId, roomId);
        ottShareRoomService.expelUserFromRoom(roomId, userId);

        return ResponseEntity.ok().build();
    }

    /**
     * 스스로 채팅방 나가기
     */
    @DeleteMapping("/self")
    public ResponseEntity<Void> leaveRoom(@AuthenticationPrincipal CustomUserDetails userDetails) {
        log.info("User ID: {} is leaving the room", userDetails.getId());
        SharingUserResponse sharingUser = sharingUserService.getSharingUser(userDetails.getId());
        Long roomId = sharingUser.getOttShareRoom().getId();

        if (sharingUser.isLeader()) {
            ottShareRoomService.deleteOttShareRoom(roomId);
        } else {
            ottShareRoomService.leaveRoom(roomId, userDetails.getId());
        }
        return ResponseEntity.ok().build();
    }

    /**
     * 체크
     */
    @PostMapping("/{room-id}/users/{user-id}/check")
    public ResponseEntity<Void> checkUserInRoom(@PathVariable("room-id") Long roomId,
                                                @PathVariable("user-id") Long userId) {
        log.info("Checking user ID: {} in room ID: {}", userId, roomId);
        ottShareRoomService.checkUserInRoom(roomId, userId);

        return ResponseEntity.ok().build();
    }

    /**
     * 아이디, 비밀번호 확인
     */
    @GetMapping("/id-password")
    public ResponseEntity<OttShareRoomIdAndPasswordResponse> getRoomIdAndPassword(@AuthenticationPrincipal CustomUserDetails userDetails) {
        log.info("Fetching ID and Password for user ID: {}", userDetails.getId());
        SharingUserResponse sharingUser = sharingUserService.getSharingUserByUserId(userDetails.getId());
        Long roomId = sharingUser.getOttShareRoom().getId();
        OttShareRoomIdAndPasswordResponse ottShareRoomIdAndPasswordResponse = ottShareRoomService.getRoomIdAndPassword(userDetails.getId(), roomId);

        return ResponseEntity.ok(ottShareRoomIdAndPasswordResponse);
    }

    /**
     * 새로운 맴버 찾기
     */
    @GetMapping("/{room-id}/new-member")
    public ResponseEntity<String> findNewMember(@PathVariable("room-id") Long roomId) {
        log.info("Finding new member for room ID: {}", roomId);
        boolean newMemberFound = ottShareRoomService.findNewMember(roomId);
        String message = newMemberFound ? "새로운 멤버를 찾았습니다" : "새로운 멤버를 찾지 못 했습니다";

        return ResponseEntity.ok(message);
    }

}
