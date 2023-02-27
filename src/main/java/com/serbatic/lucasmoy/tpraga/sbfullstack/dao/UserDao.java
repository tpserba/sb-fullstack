package com.serbatic.lucasmoy.tpraga.sbfullstack.dao;

import com.serbatic.lucasmoy.tpraga.sbfullstack.models.User;

import java.util.List;

public interface UserDao {
    List<User> getUsers();
    void deleteUser(Long id);

    void registerUser(User user);
    User obtainUserById(User user);
}
