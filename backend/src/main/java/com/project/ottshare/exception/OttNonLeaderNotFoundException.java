package com.project.ottshare.exception;

import com.project.ottshare.enums.OttType;

public class OttNonLeaderNotFoundException extends RuntimeException{

    public OttNonLeaderNotFoundException(OttType ott) {
        super(ott + "에 대한 리더가 아닌 사용자를 찾을 수 없습니다.");
    }
}
