package com.project.ottshare.dto.OttRecQuestionsDto;

import com.project.ottshare.enums.OttType;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OttRecQRequest {

    private Boolean isFirstQuestion;

    private String firstQuestion;

    private String secondQuestion;

    private OttType firstQuestionOttType;

    private OttType secondQuestionOttType;
}
