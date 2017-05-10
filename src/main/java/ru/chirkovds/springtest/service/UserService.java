package ru.chirkovds.springtest.service;

import ru.chirkovds.springtest.entity.User;

import java.util.List;

public interface UserService {
    List<User> findAll();
    User findOne(Long id);
    User saveUser(User user);
    void deleteUser(Long id);
    List<User> findByClientId(Long id);
    User findByUsername(String userName);
}
