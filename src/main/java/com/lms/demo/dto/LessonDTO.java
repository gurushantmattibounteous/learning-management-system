package com.lms.demo.dto;



import lombok.Data;

@Data
public class LessonDTO {
    private Long id;
    private String title;
    private String contentUrl;
    private Integer durationMinutes;
    private Integer orderIndex;
}