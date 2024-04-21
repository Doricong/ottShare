package com.project.ottshare.service.ottRecommendation;

import com.project.ottshare.dto.OttRecQuestionsDto.OttRecQResponse;

public interface OttRecQService {

    OttRecQResponse getOttRecQuestions(Long id);

}
