
package com.lms.demo.repository;

import com.lms.demo.model.QuizResult;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface QuizResultRepository extends JpaRepository<QuizResult, Long> {
    Optional<QuizResult> findTopByUserIdAndQuizIdOrderByAttemptedAtDesc(Long userId, Long quizId);
}