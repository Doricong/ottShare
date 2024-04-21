package com.project.ottshare.controller;

import com.project.ottshare.dto.ottShareRoom.OttSharingRoomRequest;
import com.project.ottshare.dto.waitingUserDto.WaitingUserRequest;
import com.project.ottshare.dto.waitingUserDto.WaitingUserResponse;
import com.project.ottshare.service.ottShareRoom.OttShareRoomService;
import com.project.ottshare.service.waitingUser.WaitingUserService;
import com.project.ottshare.validation.ValidationSequence;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ott-share-rooms")
public class OttShareRoomApiController {

    private final OttShareRoomService ottShareRoomService;
    private final WaitingUserService waitingUserService;

    /**
     * 인원이 다 모이면 방 생성
     */
    @PostMapping("/matchings")
    public ResponseEntity<String> matchUsersToOtt(@Validated(ValidationSequence.class) @RequestBody OttSharingRoomRequest dto) {
        //해당 ott 리더가 있는지 확인
        List<WaitingUserResponse> nonLeaderByOtt = waitingUserService.findNonLeaderByOtt(dto.getOtt());
        //해당 ott 맴버가 있는지 확인
        WaitingUserResponse leaderByOtt = waitingUserService.findLeaderByOtt(dto.getOtt());

        nonLeaderByOtt.add(leaderByOtt);

        //자동매칭 대기방에서 삭제
        waitingUserService.deleteUsers(nonLeaderByOtt);

        //ott공유방 생성
        ottShareRoomService.save(dto);

        return ResponseEntity.ok("방이 성공적으로 생성되었습니다.");
    }

}
