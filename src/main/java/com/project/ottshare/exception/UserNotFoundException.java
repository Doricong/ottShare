package com.project.ottshare.exception;


public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(Long id) {
        super("해당 유저를 찾을수 없습니다. id:" + id);
    }

    public UserNotFoundException(String message) {
        super(message);
    }
}
