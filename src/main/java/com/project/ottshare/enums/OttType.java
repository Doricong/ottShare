package com.project.ottshare.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OttType {

    NETFLIX("넷플릭스", 9000),
    TVING("티빙", 4250),
    DISNEY_PLUS("디즈니플러스", 3475);

    private final String value;
    private final int price;
}
