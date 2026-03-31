package com.lms.demo.dto;



import lombok.Data;
import java.time.LocalDateTime;

@Data
public class EnrollmentDTO {
    private Long id;
    private Long courseId;
    private String courseTitle;
    private String thumbnailUrl;
    private String status;
    private LocalDateTime enrolledAt;
}
