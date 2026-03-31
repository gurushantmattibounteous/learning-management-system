package com.lms.demo.dto;



import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AdminLessonRequest {
    @NotBlank
    private String title;
    private String contentUrl;
    private Integer durationMinutes;
    private Integer orderIndex;
}