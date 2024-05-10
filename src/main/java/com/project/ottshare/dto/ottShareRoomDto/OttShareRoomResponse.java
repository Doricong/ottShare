package com.project.ottshare.dto.ottShareRoomDto;

import com.project.ottshare.dto.sharingUserDto.OttRoomMemberResponse;
import com.project.ottshare.entity.OttShareRoom;
import com.project.ottshare.entity.SharingUser;
import com.project.ottshare.enums.OttType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;


@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class OttShareRoomResponse {

    private Long id;


    private OttType ott;

    private String ottId;

    private String ottPassword;

    public OttShareRoomResponse(OttShareRoom ottShareRoom) {
        this.id = ottShareRoom.getId();
        this.ott = ottShareRoom.getOtt();
        this.ottId = ottShareRoom.getOttId();
        this.ottPassword = ottShareRoom.getOttPassword();
    }

    public OttShareRoom toEntity() {

        return OttShareRoom.builder()
                .id(this.id)
                .ott(this.ott)
                .ottId(this.ottId)
                .ottPassword(this.ottPassword)
                .build();
    }
}
