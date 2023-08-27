package com.example.datamoduleserve.service;

import com.example.datamoduleserve.dto.InputUser;
import com.example.datamoduleserve.entity.User;

import java.util.List;

public interface UserService {

    User FindUser(String userId);
    void RegisterUser(InputUser inputUser);
    List<User> getUser(String userName);
}
