package ru.stacy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.stacy.entity.Answer;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    List<Answer> findByQuestionId(Long question_id);
}
