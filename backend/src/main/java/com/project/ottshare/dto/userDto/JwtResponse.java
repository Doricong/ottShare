package com.project.ottshare.dto.userDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Builder
@Getter
@Setter
public class JwtResponse {
    private String accessToken;
    private String refreshToken;
    private String username;
    private Long expiresIn;
    private Long refreshExpiresIn;

    public static JwtResponse from(String accessToken, String refreshToken, String username, Long expiresIn, Long refreshExpiresIn) {
        return JwtResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .username(username)
                .expiresIn(expiresIn)
                .refreshExpiresIn(refreshExpiresIn)
                .build();

    }
}