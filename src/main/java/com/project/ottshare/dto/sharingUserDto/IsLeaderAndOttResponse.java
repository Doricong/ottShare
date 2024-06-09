package com.project.ottshare.dto.sharingUserDto;

import com.project.ottshare.enums.OttType;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IsLeaderAndOttResponse {

    private boolean isLeader;

    private OttType ottType;
}
