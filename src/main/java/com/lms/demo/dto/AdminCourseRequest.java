package com.lms.demo.dto;



import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AdminCourseRequest {
    @NotBlank
    private String title;
    private String description;
    private String thumbnailUrl;
}