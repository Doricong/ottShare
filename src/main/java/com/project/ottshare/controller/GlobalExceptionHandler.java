package com.project.ottshare.controller;

import com.project.ottshare.dto.error.ErrorResponse;
import com.project.ottshare.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException ex) {
        log.warn("User not found", ex);
        return buildErrorResponse(HttpStatus.NOT_FOUND, "User not found");
    }

    @ExceptionHandler(SmsCertificationNumberMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleSmsCertificationNumberMismatchException(SmsCertificationNumberMismatchException ex) {
        log.warn("SMS certification number mismatch", ex);
        return buildErrorResponse(HttpStatus.BAD_REQUEST, "The SMS certification number does not match");
    }

    @ExceptionHandler(OttLeaderNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Object> handleOttLeaderNotFoundException(OttLeaderNotFoundException ex) {
        log.warn("OTT leader not found", ex);
        return buildErrorResponse(HttpStatus.NOT_FOUND, "The OTT leader was not found");
    }

    @ExceptionHandler(OttNonLeaderNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Object> handleOttNonLeaderNotFoundException(OttNonLeaderNotFoundException ex) {
        log.warn("OTT non-leader not found", ex);
        return buildErrorResponse(HttpStatus.NOT_FOUND, "The OTT non-leader was not found");
    }

    @ExceptionHandler(OttSharingRoomNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Object> handleOttSharingRoomNotFoundException(OttSharingRoomNotFoundException ex) {
        log.warn("OTT sharing room not found", ex);
        return buildErrorResponse(HttpStatus.NOT_FOUND, "OTT sharing room not found");
    }

    @ExceptionHandler(OttRecQNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Object> handleOttRecQNotFoundException(OttRecQNotFoundException ex) {
        log.warn("OTT question not found", ex);
        return buildErrorResponse(HttpStatus.NOT_FOUND, "OTT question not found");
    }

    @ExceptionHandler(SharingUserNotCheckedException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Object> handleSharingUserNotCheckedException(SharingUserNotCheckedException ex) {
        log.warn("Sharing user not checked", ex);
        return buildErrorResponse(HttpStatus.NOT_FOUND, "Sharing user not checked");
    }

    @ExceptionHandler(SharingUserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Object> handleSharingUserNotFoundException(SharingUserNotFoundException ex) {
        log.warn("Sharing user not found", ex);
        return buildErrorResponse(HttpStatus.NOT_FOUND, "Sharing user not found");
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Object> handleAllUncaughtException(Exception ex) {
        log.error("Unknown error occurred", ex);
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "An unknown error occurred");
    }

    private ResponseEntity<Object> buildErrorResponse(HttpStatus status, String message) {
        return ResponseEntity.status(status).body(new ErrorResponse(status.value(), message) {
        });
    }
}
