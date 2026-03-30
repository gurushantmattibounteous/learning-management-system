// CourseRepository.java
package com.lms.demo.repository;

import com.lms.demo.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {}