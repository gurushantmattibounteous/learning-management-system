package com.lms.demo.dto;



import lombok.Data;
import java.util.List;

@Data
public class CourseDTO {
    private Long id;
    private String title;
    private String description;
    private String thumbnailUrl;
    private List<ModuleDTO> modules;

    @Data
    public static class ModuleDTO {
        private Long id;
        private String title;
        private Integer orderIndex;
        private List<LessonDTO> lessons;
    }
}