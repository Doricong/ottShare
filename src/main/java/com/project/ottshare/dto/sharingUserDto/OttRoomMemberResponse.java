package com.project.ottshare.dto.sharingUserDto;

import com.project.ottshare.dto.userDto.UserResponse;
import com.project.ottshare.entity.SharingUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OttRoomMemberResponse {

    private Long id;

    private UserResponse user;

    private boolean isLeader;

    private boolean isChecked;

    // 생성자: SharingUser 엔티티를 받아서 DTO를 초기화
    public OttRoomMemberResponse(SharingUser sharingUser) {
        this.id = sharingUser.getId();
        this.user = new UserResponse(sharingUser.getUser());  // User 엔티티를 UserResponse DTO로 변환
        this.isLeader = sharingUser.isLeader();
        this.isChecked = sharingUser.isChecked();
    }

    // toEntity: DTO에서 엔티티로 변환
    public SharingUser toEntity() {
        return SharingUser.builder()
                .id(getId())
                .user(user.toEntity())  // UserResponse DTO에서 User 엔티티로 변환
                .isLeader(isLeader)
                .isChecked(isChecked)
                .build();
    }
}
