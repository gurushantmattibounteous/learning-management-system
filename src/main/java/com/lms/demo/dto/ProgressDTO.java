package com.lms.demo.dto;



import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProgressDTO {
    private Long courseId;
    private long completedLessons;
    private long totalLessons;
    private double progressPercentage;
}