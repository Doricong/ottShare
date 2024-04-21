package com.project.ottshare.dto.waitingUserDto;

import com.project.ottshare.dto.userDto.UserInfo;
import com.project.ottshare.entity.User;
import com.project.ottshare.entity.WaitingUser;
import com.project.ottshare.enums.OttType;
import com.project.ottshare.validation.ValidationGroups;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WaitingUserRequest {

    @NotNull(message = "유저 정보는 필수입니다.", groups = ValidationGroups.NotBlankGroups.class)
    private UserInfo userInfo;

    @NotNull(message = "OTT 선택은 필수 입니다.", groups = ValidationGroups.NotBlankGroups.class)
    private OttType ott;

    @NotNull(message = "leader 선택은 필수 입니다.", groups = ValidationGroups.NotBlankGroups.class)
    private Boolean isLeader;

    public WaitingUser toEntity(User user) {
        WaitingUser waitingUser = WaitingUser.builder()
                .user(user)
                .ott(ott)
                .isLeader(isLeader)
                .build();

        return waitingUser;
    }
}
