package com.project.ottshare.entity;

import com.project.ottshare.enums.OttType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ott_share_room")
public class OttShareRoom extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ott_share_room_id")
    private Long id;

    @OneToMany(mappedBy = "ottShareRoom", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SharingUser> sharingUsers = new ArrayList<>();

    @OneToMany(mappedBy = "ottShareRoom", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Message> messages = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(name = "ott", nullable = false)
    private OttType ott;

    @Column(name = "ott_id")
    private String ottId;

    @Column(name = "ott_password")
    private String ottPassword;

    // 비즈니스 로직
    public void removeUser(SharingUser sharingUser) {
        sharingUser.setOttShareRoom(null);
        sharingUsers.remove(sharingUser);
    }

    // 새로운 사용자 추가 로직
    public void addUser(SharingUser sharingUser) {
        sharingUsers.add(sharingUser);
        sharingUser.setOttShareRoom(this);
    }
}
