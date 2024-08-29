package com.project.ottshare.entity;

import com.project.ottshare.dto.waitingUserDto.WaitingUserRequest;
import com.project.ottshare.enums.OttType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "waiting_user")
public class WaitingUser extends BaseTimeEntity{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "waiting_user_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "ott_id")
    private String ottId;

    @Column(name = "ott_password")
    private String ottPassword;

    @Enumerated(EnumType.STRING)
    @Column(name = "ott", nullable = false)
    private OttType ott;

    @Column(name = "is_leader", nullable = false)
    private boolean isLeader;

    public static WaitingUser from(WaitingUserRequest request, User user) {
        return WaitingUser.builder()
                .user(user)
                .ott(request.getOtt())
                .ottId(request.getOttId())
                .ottPassword(request.getOttPassword())
                .isLeader(request.getIsLeader())
                .build();
    }
}
