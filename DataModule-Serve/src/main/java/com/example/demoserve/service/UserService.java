package com.example.demoserve.service;

import com.example.demoserve.dto.InputUser;
import com.example.demoserve.entity.User;

import java.util.List;

public interface UserService {

    User FindUser(String userId);
    void RegisterUser(InputUser inputUser);
    List<User> getUser(String userName);
}
