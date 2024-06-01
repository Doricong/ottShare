package com.project.ottshare.controller;

import com.project.ottshare.dto.OttRecQuestionsDto.OttRecQRequest;
import com.project.ottshare.dto.OttRecQuestionsDto.OttRecQResponse;
import com.project.ottshare.enums.OttType;
import com.project.ottshare.service.ottRecommendation.OttRecQServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ottRecQuestions")
public class OttRecQuestionsApiController {

    private final OttRecQServiceImpl ottRecQService;

    int netflix, tiving, wavve = 0;
    OttType resultOtt = OttType.NETFLIX;

    @GetMapping("/1")
    public ResponseEntity<OttRecQResponse> getFirstOttRecommendQuestions() {
        // 점수 모두 0으로 초기화
        netflix = 0;
        tiving = 0;
        wavve = 0;

        OttRecQResponse ottRecQuestions = ottRecQService.getOttRecQuestions(1L);

        return ResponseEntity.ok(ottRecQuestions);
    }

    @PostMapping("/16")
    public ResponseEntity<OttType> getResult() {

        if (tiving > netflix) resultOtt = OttType.TVING;
        if (wavve > netflix) resultOtt = OttType.WAVVE;

        return ResponseEntity.ok(resultOtt);
    }

    @PostMapping("/{id}")
    public void countScore(@PathVariable Long id,
                           @RequestBody OttRecQRequest ottRecQRequest) {
        Boolean isFirstQuestion = ottRecQRequest.getIsFirstQuestion();

        if (isFirstQuestion) {
            OttType firstQuestionOttType = ottRecQRequest.getFirstQuestionOttType();

            switch (firstQuestionOttType) {
                case NETFLIX -> netflix++;
                case TVING -> tiving++;
                case WAVVE -> wavve++;
            }
        } else {
            OttType secondQuestionOttType = ottRecQRequest.getSecondQuestionOttType();

            switch (secondQuestionOttType) {
                case NETFLIX -> netflix++;
                case TVING -> tiving++;
                case WAVVE -> wavve++;
            }
        }

        System.out.println("netflix = " + netflix + "점");
        System.out.println("tiving = " + tiving + "점");
        System.out.println("wavve = " + wavve + "점");

    }

    @GetMapping("/{id}")
    public ResponseEntity<OttRecQResponse> getOttRecommendQuestions(@PathVariable Long id) {
        OttRecQResponse ottRecQuestions = ottRecQService.getOttRecQuestions(id);

        return ResponseEntity.ok(ottRecQuestions);
    }



}
