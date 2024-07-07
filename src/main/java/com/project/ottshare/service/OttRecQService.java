package com.project.ottshare.service;

import com.project.ottshare.dto.ottRecQuestionsDto.OttRecQResponse;
import com.project.ottshare.entity.OttRecQuestions;
import com.project.ottshare.exception.OttRecQNotFoundException;
import com.project.ottshare.repository.OttRecQRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class OttRecQService {

    private final OttRecQRepository ottRecQuestionsRepository;

    /**
     * 양자택일 질문 가져오기
     */
    public OttRecQResponse getOttRecQuestions(Long id) {
        OttRecQuestions ottRecQuestions = ottRecQuestionsRepository.findById(id)
                .orElseThrow(() -> new OttRecQNotFoundException(id));

        return new OttRecQResponse(ottRecQuestions);
    }
}
