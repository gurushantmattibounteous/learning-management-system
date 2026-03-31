package com.lms.demo.dto;



import lombok.Data;
import java.util.List;

@Data
public class AdminQuizRequest {
    private Long courseId;
    private String title;
    private Double passPercentage;
    private List<QuestionRequest> questions;

    @Data
    public static class QuestionRequest {
        private String questionText;
        private String optionA;
        private String optionB;
        private String optionC;
        private String optionD;
        private String correctOption;
    }
}