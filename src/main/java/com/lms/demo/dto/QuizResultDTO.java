package com.lms.demo.dto;



import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class QuizResultDTO {
    private Long quizId;
    private double score;
    private boolean passed;
    private int correctAnswers;
    private int totalQuestions;
}