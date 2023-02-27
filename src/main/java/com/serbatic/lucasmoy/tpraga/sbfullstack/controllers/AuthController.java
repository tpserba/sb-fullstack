package com.serbatic.lucasmoy.tpraga.sbfullstack.controllers;

import com.serbatic.lucasmoy.tpraga.sbfullstack.dao.UserDao;
import com.serbatic.lucasmoy.tpraga.sbfullstack.models.User;
import com.serbatic.lucasmoy.tpraga.sbfullstack.utils.JWTUtil;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Manages login and everything related to authentication
 */
@RestController
@RequestMapping(value = "/api/v1")
public class AuthController {
    // Fields
    UserDao userDao;
    JWTUtil jwtUtil;

    // Constructors
    public AuthController(UserDao userDao, JWTUtil jwtUtil) {
        this.userDao = userDao;
        this.jwtUtil = jwtUtil;
    }

    // Functions
    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String login(@RequestBody User user) {
        User retrievedUser = userDao.obtainUserById(user);
        if (retrievedUser != null) {
            String jwtToken = jwtUtil.create(String.valueOf(retrievedUser.getId()), retrievedUser.getEmail());
            return jwtToken;
        }
        return "FAIL";
    }
}
