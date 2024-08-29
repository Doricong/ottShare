package com.project.ottshare.exception;

import com.project.ottshare.enums.OttType;

public class OttLeaderNotFoundException extends RuntimeException {

    public OttLeaderNotFoundException(OttType ott) {
        super(ott + "에 대한 리더를 찾을 수 없습니다.");
    }
}
