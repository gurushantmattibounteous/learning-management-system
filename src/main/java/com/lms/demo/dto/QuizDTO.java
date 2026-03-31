package com.lms.demo.dto;



import lombok.Data;
import java.util.List;

@Data
public class QuizDTO {
    private Long id;
    private String title;
    private Double passPercentage;
    private List<QuestionDTO> questions;

    @Data
    public static class QuestionDTO {
        private Long id;
        private String questionText;
        private String optionA;
        private String optionB;
        private String optionC;
        private String optionD;
        // correctOption intentionally excluded from response
    }
}