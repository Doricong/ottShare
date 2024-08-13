package com.project.ottshare.dto.ottRecQuestionsDto;

import com.project.ottshare.entity.OttRecQuestions;
import com.project.ottshare.enums.OttType;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OttRecQResponse {

    private Long id;

    private String firstQuestion;

    private String secondQuestion;

    private OttType firstQuestionOttType;

    private OttType secondQuestionOttType;

    public static OttRecQResponse from(OttRecQuestions ottRecQuestions) {
        return OttRecQResponse.builder()
                .id(ottRecQuestions.getId())
                .firstQuestion(ottRecQuestions.getFirstQuestion())
                .secondQuestion(ottRecQuestions.getSecondQuestion())
                .firstQuestionOttType(ottRecQuestions.getFirstQuestionOttType())
                .secondQuestionOttType(ottRecQuestions.getSecondQuestionOttType())
                .build();
    }
}
