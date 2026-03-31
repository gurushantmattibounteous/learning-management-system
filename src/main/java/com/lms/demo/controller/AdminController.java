package com.lms.demo.controller;



import com.lms.demo.dto.*;
import com.lms.demo.service.AdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/courses")
    public ResponseEntity<CourseDTO> createCourse(@Valid @RequestBody AdminCourseRequest req) {
        return ResponseEntity.ok(adminService.createCourse(req));
    }

    @PutMapping("/courses/{id}")
    public ResponseEntity<CourseDTO> updateCourse(@PathVariable Long id,
                                                  @Valid @RequestBody AdminCourseRequest req) {
        return ResponseEntity.ok(adminService.updateCourse(id, req));
    }

    @DeleteMapping("/courses/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        adminService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/courses/{id}/modules")
    public ResponseEntity<Void> addModule(@PathVariable Long id,
                                          @RequestBody AdminModuleRequest req) {
        adminService.addModule(id, req);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/modules/{id}/lessons")
    public ResponseEntity<Void> addLesson(@PathVariable Long id,
                                          @RequestBody AdminLessonRequest req) {
        adminService.addLesson(id, req);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/quizzes")
    public ResponseEntity<Void> createQuiz(@RequestBody AdminQuizRequest req) {
        adminService.createQuiz(req);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserSummaryDTO>> getAllUsers() {
        return ResponseEntity.ok(adminService.getAllUsers());
    }
}
