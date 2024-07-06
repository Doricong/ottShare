package com.project.ottshare.dto.userDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class JwtResponse {
//    private String grantType;
    private String token;
    private String username;
    private Long expiresIn;
}