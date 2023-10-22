package com.library.libraryservice.controller;

import com.library.libraryservice.db2.entity.User;
import com.library.libraryservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    @PreAuthorize("hasRole('ROLE_SUPER_USER')")
    public User createNewUser(@RequestBody User user) {
        return userService.createNewUser(user);
    }

    @PreAuthorize("hasRole('ROLE_SUPER_USER')")
    @PostMapping("/createMany")
    public List<User> createManyUsers(@RequestBody List<User> users) {
        return userService.createMultipleUsers(users);
    }

    @PreAuthorize("hasRole('ROLE_SUPER_USER') or " +
            "hasRole('ROLE_ADMIN')")
    @GetMapping("/getAll")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PreAuthorize("hasRole('ROLE_SUPER_USER')")
    @DeleteMapping("/deleteAll")
    public String deleteAllUsers() {
        return userService.deleteAllUsers();
    }

    @PreAuthorize("hasRole('ROLE_SUPER_USER') or " +
            "hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}")
    public User getUserById(@PathVariable("id") Long id) {
        return userService.getUserById(id);
    }
}
