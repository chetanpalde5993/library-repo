package com.library.libraryservice.controller;

import com.library.libraryservice.db2.entity.User;
import com.library.libraryservice.security_config.JwtHelper;
import com.library.libraryservice.security_config.LoginRequest;
import com.library.libraryservice.security_config.LoginResponse;
import com.library.libraryservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class LoginController {

    @Autowired
    private UserService userService;
    @Autowired
    private JwtHelper jwtHelper;
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        User user = doAuthenticate(request.getUsername(), request.getPassword());
        if(user == null) {
            exceptionHandler();
            return new ResponseEntity<>(exceptionHandler(), HttpStatus.BAD_REQUEST);
        } else {
            String token = jwtHelper.generateToken(user);
            LoginResponse response = LoginResponse.builder()
                    .token(token)
                    .username(user.getUsername()).build();
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    private User doAuthenticate(String username, String password) {
        return userService.findByUsernameAndPassword(username,password);
    }
    @ExceptionHandler(BadCredentialsException.class)
    public String exceptionHandler() {
        return "Credentials Invalid !!";
    }

    @PostMapping("/createSuperUser")
    public User createSuperUser(@RequestBody User user) {
        return userService.createNewUser(user);
    }
}
