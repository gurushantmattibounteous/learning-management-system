package com.lms.demo.controller;



import com.lms.demo.dto.QuizDTO;
import com.lms.demo.dto.QuizResultDTO;
import com.lms.demo.dto.QuizSubmissionDTO;
import com.lms.demo.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/quizzes")
@RequiredArgsConstructor
public class QuizController {

    private final QuizService quizService;

    @GetMapping("/{courseId}")
    public ResponseEntity<QuizDTO> getQuiz(@PathVariable Long courseId) {
        return ResponseEntity.ok(quizService.getQuizByCourse(courseId));
    }

    @PostMapping("/submit")
    public ResponseEntity<QuizResultDTO> submit(@RequestBody QuizSubmissionDTO submission) {
        return ResponseEntity.ok(quizService.submitQuiz(submission));
    }

    @GetMapping("/result/{quizId}")
    public ResponseEntity<QuizResultDTO> getResult(@PathVariable Long quizId) {
        return ResponseEntity.ok(quizService.getLatestResult(quizId));
    }
}