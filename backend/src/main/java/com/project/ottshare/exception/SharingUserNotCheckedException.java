package com.project.ottshare.exception;

public class SharingUserNotCheckedException extends RuntimeException{
    public SharingUserNotCheckedException(Long userId) {
        super(userId + "에 체크x");
    }
}
