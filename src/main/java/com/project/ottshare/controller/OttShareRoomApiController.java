package com.project.ottshare.controller;

import com.project.ottshare.dto.ottShareRoomDto.OttShareRoomResponse;
import com.project.ottshare.dto.sharingUserDto.SharingUserResponse;
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

    @GetMapping("/{userId}")
    public ResponseEntity<OttShareRoomResponse> getOttShareRoom(@PathVariable("userId") Long userId) {
        SharingUserResponse sharingUser = sharingUserService.getSharingUser(userId);

        OttShareRoomResponse ottShareRoom = ottShareRoomService.getOttShareRoom(sharingUser.getOttShareRoom().getId());

        return ResponseEntity.ok(ottShareRoom);
    }

}
