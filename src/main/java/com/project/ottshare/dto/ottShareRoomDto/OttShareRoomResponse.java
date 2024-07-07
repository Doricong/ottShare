package com.project.ottshare.dto.ottShareRoomDto;

import com.project.ottshare.dto.sharingUserDto.OttRoomMemberResponse;
import com.project.ottshare.entity.OttShareRoom;
import com.project.ottshare.enums.OttType;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
@Builder
public class OttShareRoomResponse {

    private Long id;
    private List<OttRoomMemberResponse> ottRoomMemberResponses = new ArrayList<>();
    private OttType ott;
    private String ottId;
    private String ottPassword;

    public OttShareRoomResponse(OttShareRoom ottShareRoom) {
        this.id = ottShareRoom.getId();
        this.ottRoomMemberResponses = ottShareRoom.getSharingUsers().stream()
                .map(OttRoomMemberResponse::new)
                .collect(Collectors.toList());
        this.ott = ottShareRoom.getOtt();
        this.ottId = ottShareRoom.getOttId();
        this.ottPassword = ottShareRoom.getOttPassword();
    }


}
