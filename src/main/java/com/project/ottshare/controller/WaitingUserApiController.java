package com.project.ottshare.controller;

import com.project.ottshare.dto.waitingUserDto.WaitingUserRequest;
import com.project.ottshare.dto.waitingUserDto.WaitingUserResponse;
import com.project.ottshare.service.waitingUser.WaitingUserService;
import com.project.ottshare.validation.ValidationSequence;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/waitingUser")
@Slf4j
public class WaitingUserApiController {

    private final WaitingUserService waitingUserService;

    /**
     * user 저장
     */
    @PostMapping("/save")
    public ResponseEntity<String> saveWaitingUser(@Validated(ValidationSequence.class) @RequestBody WaitingUserRequest dto) {
        // waitingUser에 user 저장
        log.info("dto={}", dto.getUserInfo().getPassword());
        waitingUserService.saveUser(dto);
        log.info("자동매칭 시작");
        return ResponseEntity.ok("User saved successfully");
    }


    /**
     * user 삭제
     */
    @DeleteMapping("/matchings/{matchingId}")
    public ResponseEntity<String> deleteWaitingUser(@PathVariable("matchingId") Long matchingId) {
        //user 삭제
        waitingUserService.deleteUser(matchingId);

        return ResponseEntity.ok("User deleted successfully");
    }

}
