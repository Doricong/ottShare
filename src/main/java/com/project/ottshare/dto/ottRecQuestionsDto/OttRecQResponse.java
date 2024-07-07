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

    public OttRecQResponse(OttRecQuestions ottRecQuestions) {
        this.id = ottRecQuestions.getId();
        this.firstQuestion = ottRecQuestions.getFirstQuestion();
        this.secondQuestion = ottRecQuestions.getSecondQuestion();
        this.firstQuestionOttType = ottRecQuestions.getFirstQuestionOttType();
        this.secondQuestionOttType = ottRecQuestions.getSecondQuestionOttType();
    }
}
