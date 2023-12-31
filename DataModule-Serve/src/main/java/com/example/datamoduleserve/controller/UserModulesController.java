package com.example.datamoduleserve.controller;

import com.example.datamoduleserve.dto.InputUser;
import com.example.datamoduleserve.entity.User;
import com.example.datamoduleserve.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.datamoduleserve.dao.TokenUtil.sign;

@RestController
@RequestMapping(value = "/User/UserController")
@CrossOrigin
public class UserModulesController {

    @Resource(name = "userService")
    UserService userService;

    @ResponseBody
    @RequestMapping("/userLogin")
    public String login(@RequestParam("userId") String userId,
                        @RequestParam("password") String password){
        User user =userService.FindUser(userId);
        if(user !=null) {
            if (user.getPassword().equals(password)) {
                return sign(user);
            } else {
                return "";
            }
        }
        else {
            return "not found";
        }
    }
    @ResponseBody
    @RequestMapping("/userManage")
    public void userManage(@RequestParam("id") String id){

    }

    @ResponseBody
    @RequestMapping("/register")
    public void register(@RequestParam("userId") String userId,
                         @RequestParam("password") String password){
        InputUser inputUser =new InputUser(userId,password);
        userService.RegisterUser(inputUser);
    }

    @ResponseBody
    @RequestMapping("/getUser")
    public List<User> getUser(@RequestParam("userName") String userName){
        return userService.getUser(userName);
    }
}
