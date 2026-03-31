package com.lms.demo.controller;



import com.lms.demo.dto.ProgressDTO;
import com.lms.demo.service.ProgressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/progress")
@RequiredArgsConstructor
public class ProgressController {

    private final ProgressService progressService;

    @PostMapping("/complete")
    public ResponseEntity<Void> markComplete(@RequestBody Map<String, Long> body) {
        progressService.markLessonComplete(body.get("lessonId"));
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{courseId}")
    public ResponseEntity<ProgressDTO> getCourseProgress(@PathVariable Long courseId) {
        return ResponseEntity.ok(progressService.getCourseProgress(courseId));
    }
}