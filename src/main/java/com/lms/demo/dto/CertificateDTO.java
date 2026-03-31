package com.lms.demo.dto;



import lombok.Data;
import java.time.LocalDateTime;

@Data
public class CertificateDTO {
    private Long id;
    private Long courseId;
    private String courseTitle;
    private String certificateUrl;
    private LocalDateTime issuedAt;
}