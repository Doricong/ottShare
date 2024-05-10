package com.project.ottshare.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sharing_user")
@Slf4j
public class SharingUser extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sharing_user_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ott_share_room_id")
    private OttShareRoom ottShareRoom;

    @Column(name = "is_leader", nullable = false)
    private boolean isLeader;

    @Column(name = "is_checked", nullable = false, columnDefinition = "boolean default false")
    private boolean isChecked;

    //비즈니스 로직
    public void checked() {
        this.isChecked = true;
    }

    public void addRoom(OttShareRoom room) {
        log.info("room={}", room.getId());
        this.ottShareRoom = room;
    }
}
