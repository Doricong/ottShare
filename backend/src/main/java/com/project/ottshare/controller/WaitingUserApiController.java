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
import com.project.ottshare.security.auth.CustomUserDetails;
import com.project.ottshare.service.OttShareRoomService;
import com.project.ottshare.service.SharingUserService;
import com.project.ottshare.service.WaitingUserService;
import com.project.ottshare.validation.ValidationSequence;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/waiting-users")
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

        try {
            createRoomIfPossible(dto);
            return ResponseEntity.ok("Room created successfully.");
        } catch (OttLeaderNotFoundException e) {
            return ResponseEntity.ok("No leader");
        } catch (OttNonLeaderNotFoundException e) {
            return ResponseEntity.ok("No All Member");
        }
    }

    /**
     * user 삭제
     */
    @DeleteMapping
    public ResponseEntity<String> deleteWaitingUser(@AuthenticationPrincipal CustomUserDetails userDetails) {
        log.info("Deleting waiting user with matching ID: {}", userDetails.getId());
        waitingUserService.deleteUser(userDetails.getId());

        return ResponseEntity.ok("User deleted successfully");
    }

    /**
     * userId로 user 조회
     */
    @GetMapping("/{id}")
    public ResponseEntity<Long> getWaitingUserByUserId(@AuthenticationPrincipal CustomUserDetails userDetails) {
        log.info("Fetching waiting user ID for user ID: {}", userDetails.getId());
        Long waitingUserId = waitingUserService.getWaitingUserIdByUserId(userDetails.getId())
                .orElse(0L);
//                .orElseThrow(() -> new UserNotFoundException("User not found for ID: " + userDetails.getId()));

        return ResponseEntity.ok(waitingUserId);
    }

    /**
     * 리더, ott 반환
     */
    @GetMapping("/role-and-ott")
    public ResponseEntity<IsLeaderAndOttResponse> getUserRoleAndOtt(@AuthenticationPrincipal CustomUserDetails userDetails) {
        log.info("Fetching role and OTT info for user ID: {}", userDetails.getId());
        IsLeaderAndOttResponse isLeaderAndOttResponse = waitingUserService.getWaitingUserIsLeaderAndOttByUserId(userDetails.getId())
                .orElseThrow(() -> new UserNotFoundException("User not found for ID: " + userDetails.getId()));

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
