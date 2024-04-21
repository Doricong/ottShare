package com.project.ottshare.exception;

public class SharingUserNotFoundException extends RuntimeException{

    public SharingUserNotFoundException(Long userId) {
        super("공유방의 맴버를 찾을 수 없습니다. id:" + userId);
    }
}
