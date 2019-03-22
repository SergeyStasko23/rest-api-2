package ru.stacy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.stacy.entity.Question;

public interface QuestionRepository extends JpaRepository<Question, Long> {

}
