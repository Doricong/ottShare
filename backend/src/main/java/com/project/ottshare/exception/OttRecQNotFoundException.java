package com.project.ottshare.exception;

public class OttRecQNotFoundException extends RuntimeException {

    public OttRecQNotFoundException(Long id) {
        super("해당 질문을 찾을 수 없습니다. id:" + id);
    }
}
