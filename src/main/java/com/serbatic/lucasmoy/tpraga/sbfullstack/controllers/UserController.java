package com.serbatic.lucasmoy.tpraga.sbfullstack.controllers;

import com.serbatic.lucasmoy.tpraga.sbfullstack.dao.UserDao;
import com.serbatic.lucasmoy.tpraga.sbfullstack.models.User;
import com.serbatic.lucasmoy.tpraga.sbfullstack.utils.JWTUtil;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
@RestController
@RequestMapping(path = ("/api/v1"))
public class UserController {
    private UserDao userDao;

    private JWTUtil jwtUtil;

    public UserController(UserDao userDao, JWTUtil jwtUtil) {
        this.userDao = userDao;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping(path = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getUsers(@RequestHeader(value = "Authorization") String token) {
        String userId = jwtUtil.getKey(token);
        if(userId == null) {
            return new ArrayList<>();
        }
        return userDao.getUsers();
    }

/*
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getUsers(){
        User user = new User();
        user.setId(1L);
        user.setName("John");
        List<User> users = new ArrayList<>();
        users.add(user);
        return users;
    }

 */


    @GetMapping(path = "/user/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public User getUser(@PathVariable Long id) {
        User user = new User();
        user.setId(id);
        user.setName("John");
        return user;
    }

    @PostMapping(path = "/user", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void registerUser(@RequestBody User user) {
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        String hashedPassword = argon2.hash(1, 1024, 1, user.getPassword());
        user.setPassword(hashedPassword);
        userDao.registerUser(user);
    }



    @PutMapping(path = "/user", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public User updateUser() {
        User user = new User();
        user.setName("John");
        return user;
    }

    @DeleteMapping(path = "/user/{id}")
    public void deleteUser(@PathVariable Long id) {
        userDao.deleteUser(id);
    }
}
