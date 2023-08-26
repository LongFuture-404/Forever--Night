package com.example.demoserve.dao;

import com.example.demoserve.entity.User;
import jakarta.annotation.Resource;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
@Resource(name = "UserDao")
public interface UserDao {
    User findUser(String userId);

    void registerUser(String userId,String password);
    List<User> getUser(String userName);
}
