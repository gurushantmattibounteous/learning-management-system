
package com.lms.demo.repository;

import com.lms.demo.model.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LessonRepository extends JpaRepository<Lesson, Long> {
    List<Lesson> findByModuleId(Long moduleId);
    // Used for progress % calculation
    List<Lesson> findByModuleCourseId(Long courseId);
}