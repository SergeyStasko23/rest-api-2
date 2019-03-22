package ru.stacy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.stacy.entity.User;
import ru.stacy.service.UserService;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /* http://localhost:8080/api/users/2 */
    @GetMapping("/users/{user_id}")
    public Optional<User> findUserById(@PathVariable Long user_id) {
        return userService.getUserById(user_id);
    }

    /* http://localhost:8080/api/users?username=alex */
    @GetMapping("/users")
    public Optional<User> findUserByName(@RequestParam String username) {
        return userService.getByUsername(username);
    }
}
