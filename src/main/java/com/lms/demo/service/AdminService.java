package com.lms.demo.service;

import com.lms.demo.dto.*;
import com.lms.demo.model.CourseModule;
import com.lms.demo.model.Course;
import com.lms.demo.model.Lesson;
import com.lms.demo.model.Question;
import com.lms.demo.model.Quiz;
import com.lms.demo.model.User;
import com.lms.demo.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final CourseRepository courseRepository;
    private final ModuleRepository moduleRepository;
    private final LessonRepository lessonRepository;
    private final QuizRepository quizRepository;
    private final UserRepository userRepository;
    private final EnrollmentService enrollmentService;

    // --- Course CRUD ---

    @Transactional
    public CourseDTO createCourse(AdminCourseRequest req) {
        User admin = enrollmentService.getCurrentUser();
        Course course = Course.builder()
                .title(req.getTitle())
                .description(req.getDescription())
                .thumbnailUrl(req.getThumbnailUrl())
                .createdBy(admin)
                .build();
        course = courseRepository.save(course);
        return toCourseDTO(course);
    }

    @Transactional
    public CourseDTO updateCourse(Long id, AdminCourseRequest req) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        course.setTitle(req.getTitle());
        course.setDescription(req.getDescription());
        course.setThumbnailUrl(req.getThumbnailUrl());
        return toCourseDTO(courseRepository.save(course));
    }

    @Transactional
    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
    }

    // --- Module ---

    @Transactional
    public CourseDTO.ModuleDTO addModule(Long courseId, AdminModuleRequest req) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        CourseModule module = CourseModule.builder()
                .course(course)
                .title(req.getTitle())
                .orderIndex(req.getOrderIndex())
                .build();
        module = moduleRepository.save(module);
        CourseDTO.ModuleDTO dto = new CourseDTO.ModuleDTO();
        dto.setId(module.getId());
        dto.setTitle(module.getTitle());
        dto.setOrderIndex(module.getOrderIndex());
        return dto;
    }

    // --- Lesson ---

    @Transactional
    public LessonDTO addLesson(Long moduleId, AdminLessonRequest req) {
        CourseModule module = moduleRepository.findById(moduleId)
                .orElseThrow(() -> new RuntimeException("Module not found"));
        Lesson lesson = Lesson.builder()
                .courseModule(module)
                .title(req.getTitle())
                .contentUrl(req.getContentUrl())
                .durationMinutes(req.getDurationMinutes())
                .orderIndex(req.getOrderIndex())
                .build();
        lesson = lessonRepository.save(lesson);
        LessonDTO dto = new LessonDTO();
        dto.setId(lesson.getId());
        dto.setTitle(lesson.getTitle());
        dto.setContentUrl(lesson.getContentUrl());
        dto.setDurationMinutes(lesson.getDurationMinutes());
        dto.setOrderIndex(lesson.getOrderIndex());
        return dto;
    }

    // --- Quiz ---

    @Transactional
    public void createQuiz(AdminQuizRequest req) {
        Course course = courseRepository.findById(req.getCourseId())
                .orElseThrow(() -> new RuntimeException("Course not found"));
        Quiz quiz = Quiz.builder()
                .course(course)
                .title(req.getTitle())
                .passPercentage(req.getPassPercentage() != null ? req.getPassPercentage() : 70.0)
                .build();
        List<Question> questions = req.getQuestions().stream().map(q -> Question.builder()
                .quiz(quiz)
                .questionText(q.getQuestionText())
                .optionA(q.getOptionA())
                .optionB(q.getOptionB())
                .optionC(q.getOptionC())
                .optionD(q.getOptionD())
                .correctOption(q.getCorrectOption())
                .build()
        ).toList();
        quiz.setQuestions(questions);
        quizRepository.save(quiz);
    }

    // --- Users ---

    public List<UserSummaryDTO> getAllUsers() {
        return userRepository.findAll().stream().map(u -> {
            UserSummaryDTO dto = new UserSummaryDTO();
            dto.setId(u.getId());
            dto.setName(u.getName());
            dto.setEmail(u.getEmail());
            dto.setRole(u.getRole().name());
            dto.setCreatedAt(u.getCreatedAt());
            return dto;
        }).toList();
    }

    // --- Mapper ---

    private CourseDTO toCourseDTO(Course c) {
        CourseDTO dto = new CourseDTO();
        dto.setId(c.getId());
        dto.setTitle(c.getTitle());
        dto.setDescription(c.getDescription());
        dto.setThumbnailUrl(c.getThumbnailUrl());
        return dto;
    }
}