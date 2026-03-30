
package com.lms.demo.repository;

import com.lms.demo.model.Progress;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ProgressRepository extends JpaRepository<Progress, Long> {
    List<Progress> findByUserIdAndCompleted(Long userId, boolean completed);
    Optional<Progress> findByUserIdAndLessonId(Long userId, Long lessonId);
    long countByUserIdAndLessonModuleCourseIdAndCompleted(Long userId, Long courseId, boolean completed);
}