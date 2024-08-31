package com.example.datamoduleserve.service.Impl;

import com.example.datamoduleserve.dao.UserDao;
import com.example.datamoduleserve.entity.User;
import com.example.datamoduleserve.dto.InputUser;
import com.example.datamoduleserve.service.UserService;
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
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Transactional
    public void RegisterUser(InputUser inputUser){
        try{
            userDao.registerUser(inputUser.getUserid(), inputUser.getPassword());
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    @Transactional
    public List<User> getUser(String userName){
        try{
            return userDao.getUser(userName);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }
}
