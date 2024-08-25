package com.project.ottshare.dto.ottShareRoomDto;

import com.project.ottshare.entity.OttShareRoom;
import com.project.ottshare.entity.SharingUser;
import com.project.ottshare.enums.OttType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class OttSharingRoomRequest {

    private List<SharingUser> sharingUsers = new ArrayList<>();

    private OttType ott;

    private String ottId;

    private String ottPassword;

}
