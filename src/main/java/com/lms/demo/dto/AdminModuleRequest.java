package com.lms.demo.dto;



import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AdminModuleRequest {
    @NotBlank
    private String title;
    private Integer orderIndex;
}
