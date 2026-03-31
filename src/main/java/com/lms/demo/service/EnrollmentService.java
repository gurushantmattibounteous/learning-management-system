package com.lms.demo.service;

import com.lms.demo.dto.EnrollmentDTO;
import com.lms.demo.model.Course;
import com.lms.demo.model.Enrollment;
import com.lms.demo.model.User;
import com.lms.demo.repository.CourseRepository;
import com.lms.demo.repository.EnrollmentRepository;
import com.lms.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    @Transactional
    public EnrollmentDTO enroll(Long courseId) {
        User user = getCurrentUser();
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        if (enrollmentRepository.existsByUserIdAndCourseId(user.getId(), courseId)) {
            throw new RuntimeException("Already enrolled in this course");
        }

        Enrollment enrollment = Enrollment.builder()
                .user(user)
                .course(course)
                .status(Enrollment.Status.ACTIVE)
                .build();

        return toDTO(enrollmentRepository.save(enrollment));
    }

    @Transactional(readOnly = true)  // ← this keeps the session open during mapping
    public List<EnrollmentDTO> getMyEnrollments() {
        User user = getCurrentUser();
        return enrollmentRepository.findByUserId(user.getId())
                .stream().map(this::toDTO).toList();
    }

    private EnrollmentDTO toDTO(Enrollment e) {
        EnrollmentDTO dto = new EnrollmentDTO();
        dto.setId(e.getId());
        dto.setCourseId(e.getCourse().getId());
        dto.setCourseTitle(e.getCourse().getTitle());
        dto.setThumbnailUrl(e.getCourse().getThumbnailUrl());
        dto.setStatus(e.getStatus().name());
        dto.setEnrolledAt(e.getEnrolledAt());
        return dto;
    }

    public User getCurrentUser() {
        String email = SecurityContextHolder.getContext()
                .getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
