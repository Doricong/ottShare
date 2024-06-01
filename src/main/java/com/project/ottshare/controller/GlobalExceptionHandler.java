package com.project.ottshare.controller;

import com.project.ottshare.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.NoSuchElementException;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    //사용자를 찾을 수 없는 경우를 처리하는 핸들러
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException e) {
        log.warn("User not found", e);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
    }

    //SMS 인증 번호 불일치 예외를 처리하는 핸들러
    @ExceptionHandler(SmsCertificationNumberMismatchException.class)
    public ResponseEntity<Object> handleSmsCertificationNumberMismatchException(SmsCertificationNumberMismatchException ex) {
        log.warn("SMS certification number mismatch", ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The SMS certification number does not match");
    }

    //OTT 리더를 찾을 수 없는 경우를 처리하는 핸들러
    @ExceptionHandler(OttLeaderNotFoundException.class)
    public ResponseEntity<Object> handleOttLeaderNotFoundException(OttLeaderNotFoundException ex) {
        log.warn("OTT leader not found", ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The OTT leader was not found");
    }

    //OTT 비 리더를 모두 찾을 수 없는 경우를 처리하는 핸들러
    @ExceptionHandler(OttNonLeaderNotFoundException.class)
    public ResponseEntity<Object> handleOttLeaderNotFoundException(OttNonLeaderNotFoundException ex) {
        log.warn("OTT non-leader not found", ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The OTT non-leader was not found");
    }

    //OTT 공유 방을 찾을 수 없는 경우를 처리하는 핸들러
    @ExceptionHandler(OttSharingRoomNotFoundException.class)
    public ResponseEntity<Object> handleOttSharingRoomNotFoundException(OttSharingRoomNotFoundException ex) {
        log.warn("OTT 공유 방을 찾을 수 없습니다.", ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("OTT 공유 방을 찾을 수 없습니다.");
    }

    //OTT 질문을 찾을 수 업는 경우를 처리하는 핸들러
    @ExceptionHandler(OttRecQNotFoundException.class)
    public ResponseEntity<Object> handleOttRecQNotFoundException(OttRecQNotFoundException ex) {
        log.warn("OTT 질문을 찾을 수 없습니다.", ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("OTT 질문을 찾을 수 없습니다.");
    }

    //OTT 방의 체크가 안 되어 있는 경우를 처리하는 핸들러
    @ExceptionHandler(SharingUserNotCheckedException.class)
    public ResponseEntity<Object> handleSharingUserNotCheckedException(SharingUserNotCheckedException ex) {
        log.warn("OTT 방의 체크가 안 되어 있습니다.", ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("OTT 방의 체크가 안 되어 있습니다.");
    }

    //OTT 방을 찾을 수 없습니댜,
    @ExceptionHandler(SharingUserNotFoundException.class)
    public ResponseEntity<Object> handleSharingUserNotFoundException(SharingUserNotFoundException ex) {
        log.warn("OTT 방을 찾을 수 없습니다.", ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("OTT 방을 찾을 수 없습니다.");
    }

}
