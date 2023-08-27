package com.example.webmoduleui.service.impl;

import com.example.webmoduleui.dto.User_RoleInfo;
import com.example.webmoduleui.mapper.UserMapper;
import com.example.webmoduleui.service.UserRoleService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("UserRoleService")
public class UserRoleServiceImpl implements UserRoleService {
    @Resource
    private UserMapper userMapper;
    @Override
    public List<String> getRoleByUserId(String userId) {
        List<User_RoleInfo> userRoleList = userMapper.selectRoleByUserId(userId);
        List<String> list = new ArrayList<>();
        for (User_RoleInfo ur : userRoleList) {
            list.add(ur.getRoleCode());
        }
        return list;
    }
}
