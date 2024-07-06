package com.project.ottshare.controller;

import com.project.ottshare.dto.ottRecQuestionsDto.OttRecQRequest;
import com.project.ottshare.dto.ottRecQuestionsDto.OttRecQResponse;
import com.project.ottshare.enums.OttType;
import com.project.ottshare.service.OttRecQService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ottRecQuestions")
public class OttRecQuestionsApiController {

    private final OttRecQService ottRecQService;

    @GetMapping("/first")
    public ResponseEntity<OttRecQResponse> getFirstRecommendationQuestion() {
        OttRecQResponse ottRecQuestions = ottRecQService.getOttRecQuestions(1L);
        return ResponseEntity.ok(ottRecQuestions);
    }

    @GetMapping("/result")
    public ResponseEntity<OttType> getRecommendationResult(@RequestParam int netflix, @RequestParam int tiving, @RequestParam int wavve) {
        log.info("netflix = {}점", netflix);
        log.info("tiving = {}점", tiving);
        log.info("wavve = {}점", wavve);

        OttType resultOtt = calculateResultOtt(netflix, tiving, wavve);
        return ResponseEntity.ok(resultOtt);
    }

    @PostMapping("/{id}/score")
    public void submitQuestionResponse(@PathVariable Long id,
                                       @RequestBody OttRecQRequest ottRecQRequest,
                                       @RequestParam int netflix,
                                       @RequestParam int tiving,
                                       @RequestParam int wavve) {
        if (ottRecQRequest.getIsFirstQuestion()) {
            netflix += countScoreByOttType(ottRecQRequest.getFirstQuestionOttType());
        } else {
            tiving += countScoreByOttType(ottRecQRequest.getSecondQuestionOttType());
        }

        log.info("netflix = {}점", netflix);
        log.info("tiving = {}점", tiving);
        log.info("wavve = {}점", wavve);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OttRecQResponse> getOttRecommendQuestions(@PathVariable Long id) {
        OttRecQResponse ottRecQuestions = ottRecQService.getOttRecQuestions(id);
        return ResponseEntity.ok(ottRecQuestions);
    }

    private OttType calculateResultOtt(int netflix, int tiving, int wavve) {
        if (tiving > netflix) {
            return wavve > tiving ? OttType.WAVVE : OttType.TVING;
        } else {
            return wavve > netflix ? OttType.WAVVE : OttType.NETFLIX;
        }
    }

    private int countScoreByOttType(OttType ottType) {
        switch (ottType) {
            case NETFLIX:
            case TVING:
            case WAVVE:
                return 1;
            default:
                return 0;
        }
    }
}