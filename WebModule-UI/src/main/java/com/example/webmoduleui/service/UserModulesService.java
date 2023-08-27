package com.example.webmoduleui.service;

public interface UserModulesService {

    String login(String userId, String password);

    void userManage(String id);

    void register(String userId, String password);
}
