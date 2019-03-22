package ru.stacy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.stacy.entity.Note;

import java.util.Collection;

public interface NoteRepository extends JpaRepository<Note, Long> {
    Collection<Note> findByUserUsername(String username);
}
