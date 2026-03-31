package com.lms.demo.service;

import com.lms.demo.dto.ProgressDTO;
import com.lms.demo.model.Lesson;
import com.lms.demo.model.Progress;
import com.lms.demo.model.User;
import com.lms.demo.repository.LessonRepository;
import com.lms.demo.repository.ProgressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProgressService {

    private final ProgressRepository progressRepository;
    private final LessonRepository lessonRepository;
    private final EnrollmentService enrollmentService;

    @Transactional
    public void markLessonComplete(Long lessonId) {
        User user = enrollmentService.getCurrentUser();
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new RuntimeException("Lesson not found"));

        Progress progress = progressRepository
                .findByUserIdAndLessonId(user.getId(), lessonId)
                .orElse(Progress.builder().user(user).lesson(lesson).build());

        if (!progress.isCompleted()) {
            progress.setCompleted(true);
            progress.setCompletedAt(LocalDateTime.now());
            progressRepository.save(progress);
        }
    }

    @Transactional(readOnly = true)
    public ProgressDTO getCourseProgress(Long courseId) {
        User user = enrollmentService.getCurrentUser();

        List<Lesson> allLessons = lessonRepository.findByCourseModuleCourseId(courseId);
        long total = allLessons.size();

        long completed = progressRepository
                .countByUserIdAndLessonCourseModuleCourseIdAndCompleted(user.getId(), courseId, true);

        double percentage = total == 0 ? 0 : (completed * 100.0) / total;

        return new ProgressDTO(courseId, completed, total, percentage);
    }

    @Transactional(readOnly = true)
    public boolean isCourseCompleted(Long userId, Long courseId) {
        List<Lesson> allLessons = lessonRepository.findByCourseModuleCourseId(courseId);
        long total = allLessons.size();
        if (total == 0) return false;
        long completed = progressRepository
                .countByUserIdAndLessonCourseModuleCourseIdAndCompleted(userId, courseId, true);
        return completed == total;
    }
}