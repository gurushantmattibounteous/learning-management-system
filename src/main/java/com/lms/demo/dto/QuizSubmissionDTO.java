package com.lms.demo.dto;



import lombok.Data;
import java.util.Map;

@Data
public class QuizSubmissionDTO {
    private Long quizId;
    // key = questionId, value = selected option (A/B/C/D)
    private Map<Long, String> answers;
}