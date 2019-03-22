package ru.stacy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.stacy.entity.Note;
import ru.stacy.repository.NoteRepository;

import java.util.Collection;

@Service
public class NoteService {
    private NoteRepository noteRepository;

    @Autowired
    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public Collection<Note> findByUserUsername(String username) {
        return noteRepository.findByUserUsername(username);
    }

    public void saveNote(Note note) {
        noteRepository.save(note);
    }
}
