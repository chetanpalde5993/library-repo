package com.library.libraryservice.service;

import com.library.libraryservice.db2.entity.User;

import java.util.List;

public interface UserService {

    User createNewUser(User user);

    List<User> getAllUsers();

    List<User> createMultipleUsers(List<User> users);

    String deleteAllUsers();

    User getUserById(Long id);
    User getUserByUsername(String username);

    User findByUsernameAndPassword(String username, String password);
}
