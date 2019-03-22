package ru.stacy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.stacy.entity.Answer;
import ru.stacy.exception.ResourceNotFoundException;
import ru.stacy.service.AnswerService;
import ru.stacy.service.QuestionService;

import javax.validation.Valid;
import java.util.List;

@RestController
public class AnswerController {
    private AnswerService answerService;
    private QuestionService questionService;

    @Autowired
    public AnswerController(AnswerService answerService, QuestionService questionService) {
        this.answerService = answerService;
        this.questionService = questionService;
    }

    /*
        Получить все ответы на конкретный вопрос
        http://localhost:8080/questions/1/answers
    */
    @GetMapping("/questions/{question_id}/answers")
    public List<Answer> getAnswersByQuestionId(@PathVariable Long question_id) {
        return answerService.findAnswersByQuestionId(question_id);
    }

    /*
        Добавить новый ответ на первый вопрос
        http://localhost:8080/questions/1/answers
    */
    @PostMapping("/questions/{question_id}/answers")
    public Answer addAnswer(@PathVariable Long question_id, @Valid @RequestBody Answer answer) {
        return questionService.findQuestionById(question_id)
                .map(question -> {
                    answer.setQuestion(question);
                    return answerService.saveAnswer(answer);
                })
                .orElseThrow(
                        () -> new ResourceNotFoundException("Question not found with id " + question_id)
                );
    }

    /*
       Изменить первый ответ на первый вопрос
       http://localhost:8080/questions/1/answers/1
   */
    @PutMapping("/questions/{question_id}/answers/{answer_id}")
    public Answer updateAnswer(@PathVariable Long question_id,
                               @PathVariable Long answer_id,
                               @Valid @RequestBody Answer answerRequest) {
        if(!questionService.existsQuestionById(question_id)) {
            throw new ResourceNotFoundException("Question not found with id " + question_id);
        }

        return answerService.findAnswerById(answer_id)
                .map(answer -> {
                    answer.setText(answerRequest.getText());
                    return answerService.saveAnswer(answer);
                })
                .orElseThrow(
                        () -> new ResourceNotFoundException("Answer not found with id " + answer_id)
                );
    }

    /*
      Удалить первый ответ на первый вопрос
      http://localhost:8080/questions/1/answers/1
  */
    @DeleteMapping("/questions/{question_id}/answers/{answer_id}")
    public ResponseEntity<?> deleteAnswer(@PathVariable Long question_id,
                                          @PathVariable Long answer_id) {
        if(!questionService.existsQuestionById(question_id)) {
            throw new ResourceNotFoundException("Question not found with id " + question_id);
        }

        return answerService.findAnswerById(answer_id)
                .map(answer -> {
                    answerService.deleteAnswer(answer);
                    return ResponseEntity.ok().build();
                })
                .orElseThrow(
                        () -> new ResourceNotFoundException("Answer not found with id " + answer_id)
                );

    }
}
