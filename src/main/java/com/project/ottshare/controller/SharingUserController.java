package com.project.ottshare.controller;

import com.project.ottshare.dto.sharingUserDto.IsLeaderAndOttResponse;
import com.project.ottshare.service.SharingUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/sharingUsers")
@Slf4j
public class SharingUserController {

    private final SharingUserService sharingUserService;

    /**
     * 리더, ott 반환
     */
    @GetMapping("/{userId}/role-and-ott")
    public ResponseEntity<IsLeaderAndOttResponse> getUserRoleAndOtt(@PathVariable("userId") Long userId) {
        log.info("Fetching leader and OTT information for user ID: {}", userId);

        IsLeaderAndOttResponse isLeaderAndOttResponse = sharingUserService.getSharingUserIsLeaderAndOttByUserId(userId)
                .orElse(null);
        return ResponseEntity.ok(isLeaderAndOttResponse);
    }
}
