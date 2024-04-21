package com.project.ottshare.controller;

import com.project.ottshare.dto.OttRecQuestionsDto.OttRecQResponse;
import com.project.ottshare.service.ottRecommendation.OttRecQServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ottRecQuestions")
public class OttRecQuestionsApiController {

    private final OttRecQServiceImpl ottRecQService;

    int netflix, tiving, wavve = 0;

    @GetMapping("/1")
    public ResponseEntity<OttRecQResponse> firstOttRecommendQuestions() {
        // 점수 모두 0으로 초기화
        netflix = 0;
        tiving = 0;
        wavve = 0;

        OttRecQResponse ottRecQuestions = ottRecQService.getOttRecQuestions(1L);

        return ResponseEntity.ok(ottRecQuestions);
    }


    @GetMapping("/{id}")
    public ResponseEntity<OttRecQResponse> ottRecommendQuestions(@PathVariable Long id) {
        OttRecQResponse ottRecQuestions = ottRecQService.getOttRecQuestions(id);

        return ResponseEntity.ok(ottRecQuestions);
    }



}
