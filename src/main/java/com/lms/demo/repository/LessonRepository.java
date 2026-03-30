// LessonRepository.java
package com.lms.demo.repository;

import com.lms.demo.model.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LessonRepository extends JpaRepository<Lesson, Long> {
    List<Lesson> findByCourseModuleId(Long moduleId);
    List<Lesson> findByCourseModuleCourseId(Long courseId); // ← was findByModuleCourseId
}