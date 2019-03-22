package ru.stacy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.stacy.entity.Answer;
import ru.stacy.repository.AnswerRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AnswerService {
    private AnswerRepository answerRepository;

    @Autowired
    public AnswerService(AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }

    public List<Answer> findAnswersByQuestionId(Long question_id) {
        return answerRepository.findByQuestionId(question_id);
    }

    public Answer saveAnswer(Answer answer) {
        return answerRepository.save(answer);
    }

    public Optional<Answer> findAnswerById(Long answer_id) {
        return answerRepository.findById(answer_id);
    }

    public void deleteAnswer(Answer answer) {
        answerRepository.delete(answer);
    }
}
