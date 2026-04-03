package com.lms.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuizResultDTO {
    private Long quizId;
    private Long courseId;       // ADDED
    private double score;
    private boolean passed;
    private int correctAnswers;
    private int totalQuestions;
    private LocalDateTime attemptedAt; // ADDED
}