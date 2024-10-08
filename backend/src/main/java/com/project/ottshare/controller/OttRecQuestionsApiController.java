package com.project.ottshare.controller;

import com.project.ottshare.dto.ottRecQuestionsDto.OttRecQResponse;
import com.project.ottshare.service.OttRecQService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ott-rec-questions")
public class OttRecQuestionsApiController {

    private final OttRecQService ottRecQService;

    @GetMapping("/{id}")
    public ResponseEntity<OttRecQResponse> getOttRecommendQuestions(@PathVariable Long id) {
        OttRecQResponse ottRecQuestions = ottRecQService.getOttRecQuestions(id);
        return ResponseEntity.ok(ottRecQuestions);
    }

}