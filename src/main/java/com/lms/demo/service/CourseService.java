package com.lms.demo.service;



import com.lms.demo.dto.CourseDTO;
import com.lms.demo.dto.LessonDTO;
import com.lms.demo.model.Course;
import com.lms.demo.model.Lesson;
import com.lms.demo.model.Module;
import com.lms.demo.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;

    public List<CourseDTO> getAllCourses() {
        return courseRepository.findAll().stream()
                .map(this::toSimpleDTO)
                .toList();
    }

    public CourseDTO getCourseById(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        return toFullDTO(course);
    }

    // --- Mappers ---

    private CourseDTO toSimpleDTO(Course course) {
        CourseDTO dto = new CourseDTO();
        dto.setId(course.getId());
        dto.setTitle(course.getTitle());
        dto.setDescription(course.getDescription());
        dto.setThumbnailUrl(course.getThumbnailUrl());
        return dto;
    }

    private CourseDTO toFullDTO(Course course) {
        CourseDTO dto = toSimpleDTO(course);
        if (course.getModules() != null) {
            dto.setModules(course.getModules().stream()
                    .map(this::toModuleDTO)
                    .toList());
        }
        return dto;
    }

    private CourseDTO.ModuleDTO toModuleDTO(Module module) {
        CourseDTO.ModuleDTO dto = new CourseDTO.ModuleDTO();
        dto.setId(module.getId());
        dto.setTitle(module.getTitle());
        dto.setOrderIndex(module.getOrderIndex());
        if (module.getLessons() != null) {
            dto.setLessons(module.getLessons().stream()
                    .map(this::toLessonDTO)
                    .toList());
        }
        return dto;
    }

    private LessonDTO toLessonDTO(Lesson lesson) {
        LessonDTO dto = new LessonDTO();
        dto.setId(lesson.getId());
        dto.setTitle(lesson.getTitle());
        dto.setContentUrl(lesson.getContentUrl());
        dto.setDurationMinutes(lesson.getDurationMinutes());
        dto.setOrderIndex(lesson.getOrderIndex());
        return dto;
    }
}