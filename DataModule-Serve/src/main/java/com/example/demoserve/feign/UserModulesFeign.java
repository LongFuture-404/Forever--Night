package com.example.demoserve.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@FeignClient(name = "Demo-Serve",url = "http://localhost:8001/User/UserController")
public interface UserModulesFeign {

    @ResponseBody
    @RequestMapping("/userLogin")
    String login(@RequestParam("userId") String userId,
                 @RequestParam("password") String password);

    @ResponseBody
    @RequestMapping("/userManage")
    void userManage(@RequestParam("id") String id);

    @ResponseBody
    @RequestMapping("/register")
    void register(@RequestParam("userId") String userId,
                  @RequestParam("password") String password);
}
