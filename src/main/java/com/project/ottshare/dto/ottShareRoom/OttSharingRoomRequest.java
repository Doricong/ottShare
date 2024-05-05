package com.project.ottshare.dto.ottShareRoom;

import com.project.ottshare.entity.OttShareRoom;
import com.project.ottshare.entity.SharingUser;
import com.project.ottshare.entity.User;
import com.project.ottshare.enums.OttType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class OttSharingRoomRequest {

    private List<SharingUser> sharingUsers = new ArrayList<>();

    private OttType ott;

    public OttShareRoom toEntity() {
        OttShareRoom ottShareRoom = OttShareRoom.builder()
                .sharingUsers(getSharingUsers())
                .ott(getOtt())
                .build();

        return ottShareRoom;
    }
}
