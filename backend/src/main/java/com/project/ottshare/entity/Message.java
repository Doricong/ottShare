package com.project.ottshare.entity;

import com.project.ottshare.dto.ottShareRoomDto.MessageRequest;
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
@Table(name = "message")
public class Message extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ott_share_room_id")
    private OttShareRoom ottShareRoom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sharing_user_id")
    private SharingUser sharingUser;

    @Column(name = "message", nullable = false)
    private String message;

    public void setOttShareRoom(OttShareRoom room) {
        this.ottShareRoom = room;
    }

    public void setSharingUser(SharingUser user) {
        this.sharingUser = user;
    }

    public static Message from(MessageRequest messageRequest) {
        OttShareRoom roomEntity = OttShareRoom.from(messageRequest.getOttShareRoom());
        SharingUser userEntity = SharingUser.from(messageRequest.getOttRoomMemberResponse());

        return Message.builder()
                .ottShareRoom(roomEntity)
                .sharingUser(userEntity)
                .message(messageRequest.getMessage())
                .build();
    }
}
