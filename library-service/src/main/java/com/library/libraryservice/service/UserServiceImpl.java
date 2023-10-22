package com.library.libraryservice.service;

import com.library.libraryservice.db2.entity.User;
import com.library.libraryservice.db2.repository.UserRepository;
import com.library.libraryservice.db3.entity.M_User;
import com.library.libraryservice.db3.repository.UserMongoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMongoRepository userMongoRepository;

    @Override
    public User createNewUser(User user) {
        userMongoRepository.save(userToMUser(user));
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public List<User> createMultipleUsers(List<User> users) {
        List<M_User> mUsers = users.stream()
                        .map(user -> new M_User(
                user.getName(),user.getUsername(), user.getPassword(),user.getPhoneNumber()))
                .collect(Collectors.toList());
        userMongoRepository.saveAll(mUsers);
        return userRepository.saveAll(users);
    }

    @Override
    public String deleteAllUsers() {
        userRepository.deleteAll();
        userMongoRepository.deleteAll();
        return "Deleted all records";
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User findByUsernameAndPassword(String username, String password) {
        //Here, do password encrypting for checking
        return userRepository.findByUsernameAndPassword(username,password);
    }

    private M_User userToMUser(User user) {
        return new M_User(
                user.getName(),user.getUsername(), user.getPassword(),user.getPhoneNumber());
    }
}
