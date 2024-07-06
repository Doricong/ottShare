package com.project.ottshare.controller;

import com.project.ottshare.dto.ottShareRoomDto.OttShareRoomResponse;
import com.project.ottshare.dto.ottShareRoomDto.OttSharingRoomRequest;
import com.project.ottshare.dto.sharingUserDto.IsLeaderAndOttResponse;
import com.project.ottshare.dto.waitingUserDto.WaitingUserRequest;
import com.project.ottshare.dto.waitingUserDto.WaitingUserResponse;
import com.project.ottshare.entity.SharingUser;
import com.project.ottshare.exception.OttLeaderNotFoundException;
import com.project.ottshare.exception.OttNonLeaderNotFoundException;
import com.project.ottshare.exception.UserNotFoundException;
import com.project.ottshare.service.OttShareRoomService;
import com.project.ottshare.service.SharingUserService;
import com.project.ottshare.service.WaitingUserService;
import com.project.ottshare.validation.ValidationSequence;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/waitingUsers")
@Slf4j
public class WaitingUserApiController {

    private final WaitingUserService waitingUserService;
    private final OttShareRoomService ottShareRoomService;
    private final SharingUserService sharingUserService;

    /**
     * user 저장
     */
    @PostMapping
    public ResponseEntity<String> createWaitingUser(@Validated(ValidationSequence.class) @RequestBody WaitingUserRequest dto) {
        log.info("Saving user data: {}", dto.getOtt());
        waitingUserService.createWaitingUser(dto);
        createRoomIfPossible(dto);

        return ResponseEntity.ok("Room created successfully.");

    }

    /**
     * user 삭제
     */
    @DeleteMapping("/{matchingId}")
    public ResponseEntity<String> deleteWaitingUser(@PathVariable("matchingId") Long matchingId) {
        log.info("Deleting waiting user with matching ID: {}", matchingId);
        waitingUserService.deleteUser(matchingId);

        return ResponseEntity.ok("User deleted successfully");
    }

    /**
     * userId로 user 조회
     */
    @GetMapping("/{userId}")
    public ResponseEntity<Long> getWaitingUserId(@PathVariable("userId") Long userId) {
        log.info("Fetching waiting user ID for user ID: {}", userId);
        Long waitingUserId = waitingUserService.getWaitingUserIdByUserId(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found for ID: " + userId));

        return ResponseEntity.ok(waitingUserId);
    }

    /**
     * 리더, ott 반환
     */
    @GetMapping("/{userId}/role-and-ott")
    public ResponseEntity<IsLeaderAndOttResponse> getUserRoleAndOtt(@PathVariable("userId") Long userId) {
        log.info("Fetching role and OTT info for user ID: {}", userId);
        IsLeaderAndOttResponse isLeaderAndOttResponse = waitingUserService.getWaitingUserIsLeaderAndOttByUserId(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found for ID: " + userId));

        return ResponseEntity.ok(isLeaderAndOttResponse);
    }

    private void createRoomIfPossible(WaitingUserRequest dto) throws OttLeaderNotFoundException, OttNonLeaderNotFoundException {
        List<WaitingUserResponse> members = waitingUserService.getNonLeaderByOtt(dto.getOtt());
        WaitingUserResponse leader = waitingUserService.getLeaderByOtt(dto.getOtt());

        members.add(leader); // 리더 정보를 리스트에 추가

        // 인원 수가 충분하면 자동 매칭 대기방에서 사용자 삭제 및 방을 생성
        waitingUserService.deleteUsers(members);

        List<SharingUser> sharingUsers = sharingUserService.prepareSharingUsers(members);
        String ottId = leader.getOttId();
        String ottPassword = leader.getOttPassword();

        OttSharingRoomRequest ottSharingRoomRequest = new OttSharingRoomRequest(sharingUsers, dto.getOtt(), ottId, ottPassword);
        Long savedOttShareRoomId = ottShareRoomService.createOttShareRoom(ottSharingRoomRequest); // 방 생성 로직
        OttShareRoomResponse ottShareRoom = ottShareRoomService.getOttShareRoom(savedOttShareRoomId);

        sharingUserService.associateRoomWithSharingUsers(sharingUsers, ottShareRoom);
        log.info("Room created successfully for OTT service: {}", dto.getOtt());
    }
}
