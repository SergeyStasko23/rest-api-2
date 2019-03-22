package ru.stacy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.stacy.entity.Note;
import ru.stacy.entity.User;
import ru.stacy.exception.UserNotFoundException;
import ru.stacy.service.NoteService;
import ru.stacy.service.UserService;

import java.util.Collection;
import java.util.Optional;

/*
    http://localhost:8080/stacy/notes
    ПОЛУЧЕНИЕ ВСЕХ ЗАПИСЕЙ ПОЛЬЗОВАТЕЛЯ stacy
*/

@RestController
@RequestMapping("/api/{username}/notes")
public class NotesController {
    private NoteService noteService;
    private UserService userService;

    @Autowired
    public NotesController(NoteService noteService, UserService userService) {
        this.noteService = noteService;
        this.userService = userService;
    }

    @GetMapping
    public Collection<Note> findAllNotes(@PathVariable String username) {
        Optional<User> user = userService.getByUsername(username);
        user.orElseThrow(() -> new UserNotFoundException(username));

        return noteService.findByUserUsername(username);
    }

    @PostMapping
    public void addNote(@PathVariable String username, @RequestBody Note note) {
        User user = userService.getByUsername(username)
                .orElseThrow(
                        () -> new UserNotFoundException(username)
                );

        note.setUser(user);
        noteService.saveNote(note);
    }
}
