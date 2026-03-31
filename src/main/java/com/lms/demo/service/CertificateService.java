package com.lms.demo.service;

import com.lms.demo.dto.CertificateDTO;
import com.lms.demo.model.Certificate;
import com.lms.demo.model.Course;
import com.lms.demo.model.QuizResult;
import com.lms.demo.model.User;
import com.lms.demo.repository.CertificateRepository;
import com.lms.demo.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CertificateService {

    private final CertificateRepository certificateRepository;
    private final CourseRepository courseRepository;
    private final ProgressService progressService;
    private final QuizService quizService;
    private final EnrollmentService enrollmentService;

    @Transactional
    public CertificateDTO generate(Long courseId) {
        User user = enrollmentService.getCurrentUser();
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        if (certificateRepository.existsByUserIdAndCourseId(user.getId(), courseId)) {
            throw new RuntimeException("Certificate already issued");
        }

        boolean courseComplete = progressService.isCourseCompleted(user.getId(), courseId);
        if (!courseComplete) {
            throw new RuntimeException("Complete all lessons before generating certificate");
        }

        QuizResult quizResult = quizService.getPassedResult(user.getId(), courseId);
        if (quizResult == null || !quizResult.getPassed()) {
            throw new RuntimeException("Pass the quiz before generating certificate");
        }

        Certificate cert = Certificate.builder()
                .user(user)
                .course(course)
                .certificateUrl("/certificates/" + user.getId() + "/" + courseId)
                .build();

        return toDTO(certificateRepository.save(cert));
    }

    @Transactional(readOnly = true)
    public List<CertificateDTO> getMyCertificates() {
        User user = enrollmentService.getCurrentUser();
        return certificateRepository.findByUserId(user.getId())
                .stream().map(this::toDTO).toList();
    }

    private CertificateDTO toDTO(Certificate c) {
        CertificateDTO dto = new CertificateDTO();
        dto.setId(c.getId());
        dto.setCourseId(c.getCourse().getId());
        dto.setCourseTitle(c.getCourse().getTitle());
        dto.setCertificateUrl(c.getCertificateUrl());
        dto.setIssuedAt(c.getIssuedAt());
        return dto;
    }
}