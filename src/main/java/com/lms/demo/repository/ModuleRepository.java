package com.lms.demo.repository;



import com.lms.demo.model.CourseModule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ModuleRepository extends JpaRepository<CourseModule, Long> {}