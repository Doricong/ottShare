package com.project.ottshare.entity;

import com.project.ottshare.enums.OttType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ott_recommendation_questions")
public class OttRecQuestions extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ott_recommendation_questions_id")
    private Long id;

    private String firstQuestion;

    private String secondQuestion;

    @Enumerated(EnumType.STRING)
    private OttType firstQuestionOttType;

    @Enumerated(EnumType.STRING)
    private OttType secondQuestionOttType;

}
