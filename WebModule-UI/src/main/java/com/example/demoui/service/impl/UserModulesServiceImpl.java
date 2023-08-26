package com.example.demoui.service.impl;

import com.example.demoserve.feign.UserModulesFeign;
import com.example.demoui.service.UserModulesService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service(value = "userModulesService")
public class UserModulesServiceImpl implements UserModulesService {

    @Resource
    private UserModulesFeign userModulesFeign;

    @Override
    public String login(String userId, String password){
        return userModulesFeign.login(userId,password);
    }

    @Override
    public void userManage(String id){
        userModulesFeign.userManage(id);
    }

    @Override
    public void register(String userId, String password){
        userModulesFeign.register(userId,password);
    }
}
