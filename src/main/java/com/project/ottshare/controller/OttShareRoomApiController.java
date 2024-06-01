package com.project.ottshare.controller;

import com.project.ottshare.dto.ottShareRoom.OttShareRoomIdAndPasswordResponse;
import com.project.ottshare.dto.ottShareRoomDto.OttShareRoomResponse;
import com.project.ottshare.dto.sharingUserDto.SharingUserResponse;
import com.project.ottshare.service.ottShareRoom.OttShareRoomService;
import com.project.ottshare.service.ottShareRoom.OttShareRoomServiceImpl;
import com.project.ottshare.service.sharingUser.SharingUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import retrofit2.http.Path;

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
        SharingUserResponse sharingUser = sharingUserService.getSharingUser(userId);

        OttShareRoomResponse ottShareRoom = ottShareRoomService.getOttShareRoom(sharingUser.getOttShareRoom().getId());

        return ResponseEntity.ok(ottShareRoom);
    }

    /**
     * 체크
     */
    @PostMapping("/{roomId}/user/{userId}/check")
    public ResponseEntity getOttShareRoom(@PathVariable("roomId") Long roomId, @PathVariable("userId") Long userId) {
        ottShareRoomService.checkUser(roomId, userId);
        return ResponseEntity.ok().build();
    }

    /**
     * 아이디, 비밀번호 확인
     */
    @GetMapping("/{userId}/idPassword")
    public ResponseEntity<OttShareRoomIdAndPasswordResponse> getIdAndPassword(@PathVariable("userId") Long userId) {
        SharingUserResponse sharingUser = sharingUserService.getSharingUser(userId);

        Long roomId = sharingUser.getOttShareRoom().getId();

        OttShareRoomIdAndPasswordResponse ottShareRoomIdAndPasswordResponse = ottShareRoomService.idAndPassword(userId, roomId);

        return ResponseEntity.ok(ottShareRoomIdAndPasswordResponse);
    }
}
