package com.project.ottshare.controller;

import com.project.ottshare.dto.ottShareRoom.OttShareRoomIdAndPasswordResponse;
import com.project.ottshare.dto.ottShareRoomDto.OttShareRoomResponse;
import com.project.ottshare.dto.sharingUserDto.SharingUserResponse;
import com.project.ottshare.dto.userDto.UserResponse;
import com.project.ottshare.service.ottShareRoom.OttShareRoomService;
import com.project.ottshare.service.sharingUser.SharingUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/ottShareRoom")
@Slf4j
public class OttShareRoomApiController {

    private final OttShareRoomService ottShareRoomService;
    private final SharingUserService sharingUserService;


    /**
     * 채팅방
     */
    @GetMapping("/{userId}")
    public ResponseEntity<OttShareRoomResponse> getOttShareRoom(@PathVariable("userId") Long userId) {
        SharingUserResponse sharingUser = sharingUserService.getSharingUserByUserId(userId);

        OttShareRoomResponse ottShareRoom = ottShareRoomService.getOttShareRoom(sharingUser.getOttShareRoom().getId());

        return ResponseEntity.ok(ottShareRoom);
    }

    /**
     * 강제퇴장
     */
    @DeleteMapping("/{roomId}/user/{userId}/kick")
    public ResponseEntity<Void> kickOttShareRoom(@PathVariable("roomId") Long roomId,
                                                 @PathVariable("userId") Long userId) {
        ottShareRoomService.expelUser(roomId, userId);
        return ResponseEntity.ok().build();
    }

    /**
     * 스스로 채팅방 나가기
     */
    @DeleteMapping("/{roomId}/user/{userId}/leave")
    public ResponseEntity<Void> leaveRoom(@PathVariable("roomId") Long roomId,
                                          @PathVariable("userId") Long userId) {
        log.info("1111");
        SharingUserResponse sharingUser = sharingUserService.getSharingUser(userId);
        //나가는 사람이 리더면 공유방 제거
        if (sharingUser.isLeader()) {
            ottShareRoomService.removeOttShareRoom(roomId);
            log.info("2222");
        } else {
            ottShareRoomService.leaveRoom(roomId, userId);
        }
        log.info("3333");
        return ResponseEntity.ok().build();
    }

    /**
     * 체크
     */
    @PostMapping("/{roomId}/user/{userId}/check")
    public ResponseEntity<Void> getOttShareRoom(@PathVariable("roomId") Long roomId, @PathVariable("userId") Long userId) {
        ottShareRoomService.checkUser(roomId, userId);
        return ResponseEntity.ok().build();
    }

    /**
     * 아이디, 비밀번호 확인
     */
    @GetMapping("/{userId}/idPassword")
    public ResponseEntity<OttShareRoomIdAndPasswordResponse> getIdAndPassword(@PathVariable("userId") Long userId) {
        SharingUserResponse sharingUser = sharingUserService.getSharingUserByUserId(userId);

        Long roomId = sharingUser.getOttShareRoom().getId();

        OttShareRoomIdAndPasswordResponse ottShareRoomIdAndPasswordResponse = ottShareRoomService.idAndPassword(userId, roomId);

        return ResponseEntity.ok(ottShareRoomIdAndPasswordResponse);
    }

    /**
     * 새로운 맴버 찾기
     */
    @GetMapping("/{roomId}/findNewMember")
    public ResponseEntity<String> findNewMember(@PathVariable("roomId") Long roomId) {
        boolean newMemberFound = ottShareRoomService.findNewMember(roomId);
        String message = newMemberFound ? "새로운 멤버를 찾았습니다" : "새로운 멤버를 찾지 못 했습니다";
        return ResponseEntity.ok(message);
    }
}
