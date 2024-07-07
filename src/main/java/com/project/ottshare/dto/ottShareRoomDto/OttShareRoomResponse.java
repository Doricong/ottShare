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


    public static OttShareRoomResponse from(OttShareRoom ottShareRoom) {
        return OttShareRoomResponse.builder()
                .id(ottShareRoom.getId())
                .ottRoomMemberResponses(ottShareRoom.getSharingUsers().stream()
                        .map(OttRoomMemberResponse::from)
                        .collect(Collectors.toList()))
                .ott(ottShareRoom.getOtt())
                .ottId(ottShareRoom.getOttId())
                .ottPassword(ottShareRoom.getOttPassword())
                .build();
    }


}
