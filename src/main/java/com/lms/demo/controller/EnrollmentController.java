package com.lms.demo.controller;



import com.lms.demo.dto.EnrollmentDTO;
import com.lms.demo.service.EnrollmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/enrollments")
@RequiredArgsConstructor
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    @PostMapping
    public ResponseEntity<EnrollmentDTO> enroll(@RequestBody Map<String, Long> body) {
        return ResponseEntity.ok(enrollmentService.enroll(body.get("courseId")));
    }

    @GetMapping("/my")
    public ResponseEntity<List<EnrollmentDTO>> getMyEnrollments() {
        return ResponseEntity.ok(enrollmentService.getMyEnrollments());
    }
}