package com.lms.demo.service;

import com.lms.demo.dto.QuizDTO;
import com.lms.demo.dto.QuizResultDTO;
import com.lms.demo.dto.QuizSubmissionDTO;
import com.lms.demo.model.Question;
import com.lms.demo.model.Quiz;
import com.lms.demo.model.QuizResult;
import com.lms.demo.model.User;
import com.lms.demo.repository.QuizRepository;
import com.lms.demo.repository.QuizResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class QuizService {

    private final QuizRepository quizRepository;
    private final QuizResultRepository quizResultRepository;
    private final EnrollmentService enrollmentService;

    @Transactional(readOnly = true)
    public QuizDTO getQuizByCourse(Long courseId) {
        Quiz quiz = quizRepository.findByCourseId(courseId)
                .orElseThrow(() -> new RuntimeException("Quiz not found for course"));
        return toDTO(quiz);
    }

    @Transactional
    public QuizResultDTO submitQuiz(QuizSubmissionDTO submission) {
        User user = enrollmentService.getCurrentUser();

        Quiz quiz = quizRepository.findById(submission.getQuizId())
                .orElseThrow(() -> new RuntimeException("Quiz not found"));

        List<Question> questions = quiz.getQuestions();
        Map<Long, String> answers = submission.getAnswers();

        int correct = 0;
        for (Question q : questions) {
            String selected = answers.get(q.getId());
            if (selected != null && selected.equalsIgnoreCase(q.getCorrectOption())) {
                correct++;
            }
        }

        int total = questions.size();
        double score = total == 0 ? 0 : (correct * 100.0) / total;
        boolean passed = score >= quiz.getPassPercentage();

        QuizResult result = QuizResult.builder()
                .user(user)
                .quiz(quiz)
                .score(score)
                .passed(passed)
                .build();
        quizResultRepository.save(result);

        return new QuizResultDTO(quiz.getId(), score, passed, correct, total);
    }

    @Transactional(readOnly = true)
    public QuizResultDTO getLatestResult(Long quizId) {
        User user = enrollmentService.getCurrentUser();
        QuizResult result = quizResultRepository
                .findTopByUserIdAndQuizIdOrderByAttemptedAtDesc(user.getId(), quizId)
                .orElseThrow(() -> new RuntimeException("No result found"));

        return new QuizResultDTO(
                result.getQuiz().getId(),
                result.getScore(),
                result.getPassed(),
                0, 0
        );
    }

    @Transactional(readOnly = true)
    public QuizResult getPassedResult(Long userId, Long courseId) {
        Quiz quiz = quizRepository.findByCourseId(courseId)
                .orElseThrow(() -> new RuntimeException("Quiz not found"));
        return quizResultRepository
                .findTopByUserIdAndQuizIdOrderByAttemptedAtDesc(userId, quiz.getId())
                .orElse(null);
    }

    private QuizDTO toDTO(Quiz quiz) {
        QuizDTO dto = new QuizDTO();
        dto.setId(quiz.getId());
        dto.setTitle(quiz.getTitle());
        dto.setPassPercentage(quiz.getPassPercentage());
        dto.setQuestions(quiz.getQuestions().stream().map(q -> {
            QuizDTO.QuestionDTO qd = new QuizDTO.QuestionDTO();
            qd.setId(q.getId());
            qd.setQuestionText(q.getQuestionText());
            qd.setOptionA(q.getOptionA());
            qd.setOptionB(q.getOptionB());
            qd.setOptionC(q.getOptionC());
            qd.setOptionD(q.getOptionD());
            return qd;
        }).toList());
        return dto;
    }
}