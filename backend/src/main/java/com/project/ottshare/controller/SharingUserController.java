package com.project.ottshare.controller;

import com.project.ottshare.dto.sharingUserDto.IsLeaderAndOttResponse;
import com.project.ottshare.security.auth.CustomUserDetails;
import com.project.ottshare.service.SharingUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/sharing-users")
@Slf4j
public class SharingUserController {

    private final SharingUserService sharingUserService;

    /**
     * 리더, ott 반환
     */
    @GetMapping("/role-and-ott")
    public ResponseEntity<IsLeaderAndOttResponse> getUserRoleAndOtt(@AuthenticationPrincipal CustomUserDetails userDetails) {
        log.info("Fetching leader and OTT information for user ID: {}", userDetails.getId());
        IsLeaderAndOttResponse isLeaderAndOttResponse = sharingUserService.getSharingUserIsLeaderAndOttByUserId(userDetails.getId())
                .orElse(null);

        return ResponseEntity.ok(isLeaderAndOttResponse);
    }
}
