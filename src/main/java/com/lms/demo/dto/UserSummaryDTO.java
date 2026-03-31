package com.lms.demo.dto;


import lombok.Data;
import java.time.LocalDateTime;

@Data
public class UserSummaryDTO {
    private Long id;
    private String name;
    private String email;
    private String role;
    private LocalDateTime createdAt;
}