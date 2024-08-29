package com.project.ottshare.exception;

public class OttSharingRoomNotFoundException extends RuntimeException{

    public OttSharingRoomNotFoundException(Long id) {
        super("해당 ott공유 방을 찾을 수 없습니다. id: " + id);
    }

    public OttSharingRoomNotFoundException(String message) {
        super(message);
    }
}
