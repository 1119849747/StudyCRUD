package com.longsong.myspringboot.service;

import com.longsong.myspringboot.entity.User;

import java.util.List;

public interface UserService {

  List<User> getAllUsers();
  User addUser(User user);
  User getUserById(Integer id);
    void deleteUser(Integer id);
}