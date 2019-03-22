package ru.stacy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.stacy.entity.Question;
import ru.stacy.exception.ResourceNotFoundException;
import ru.stacy.service.QuestionService;

import javax.validation.Valid;

@RestController
public class QuestionController {
    private QuestionService questionService;

    @Autowired
    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    /*
        Получить все вопросы
        http://localhost:8080/questions
    */
    @GetMapping("/questions")
    public Page<Question> getQuestions(Pageable pageable) {
        return questionService.findAllQuestions(pageable);
    }

    /*
        Создать новый вопрос
        http://localhost:8080/questions
    */
    @PostMapping("/questions")
    public Question createQuestion(@Valid @RequestBody Question question) {
        return questionService.saveQuestion(question);
    }

    /*
        Изменить первый вопрос
        http://localhost:8080/questions/1
    */
    @PutMapping("/questions/{question_id}")
    public Question updateQuestion(@PathVariable Long question_id,
                                   @Valid @RequestBody Question questionRequest) {
        return questionService.findQuestionById(question_id)
                .map(question -> {
                    question.setTitle(questionRequest.getTitle());
                    question.setDescription(questionRequest.getDescription());
                    return questionService.saveQuestion(question);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Question not found with id " + question_id));
    }

    /*
        Удалить первый вопрос
        http://localhost:8080/questions/1
    */
    @DeleteMapping("/questions/{question_id}")
    public ResponseEntity<?> deleteQuestion(@PathVariable Long question_id) {
        return questionService.findQuestionById(question_id)
                .map(question -> {
                    questionService.deleteQuestion(question);
                    return ResponseEntity.ok().build();
                })
                .orElseThrow(() -> new ResourceNotFoundException("Question not found with id " + question_id));
    }
}
