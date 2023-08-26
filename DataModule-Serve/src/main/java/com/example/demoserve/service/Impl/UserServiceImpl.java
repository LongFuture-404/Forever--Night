package com.example.demoserve.service.Impl;

import com.example.demoserve.dao.UserDao;
import com.example.demoserve.entity.User;
import com.example.demoserve.dto.InputUser;
import com.example.demoserve.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Resource(name = "userDao")
    UserDao userDao;
    @Transactional
    public User FindUser(String userId){
        try{
            return userDao.findUser(userId);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Transactional
    public void RegisterUser(InputUser inputUser){
        try{
            userDao.registerUser(inputUser.getUserid(), inputUser.getPassword());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Transactional
    public List<User> getUser(String userName){
        try{
            return userDao.getUser(userName);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
