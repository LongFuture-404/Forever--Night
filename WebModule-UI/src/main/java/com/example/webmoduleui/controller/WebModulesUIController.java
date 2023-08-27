package com.example.webmoduleui.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.datamoduleserve.entity.User;
import com.example.webmoduleui.Utils.RSA;
import com.example.webmoduleui.Utils.RSAUtils;
import com.example.webmoduleui.annotation.RequireRoles;
import com.example.webmoduleui.dto.PermMenuTree;
import com.example.webmoduleui.dto.QueryPage;
import com.example.webmoduleui.service.UIService;
import com.example.webmoduleui.service.UserModulesService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

@RestController
@CrossOrigin
public class WebModulesUIController {
    RSA rsa = new RSA();
    @Resource(name = "userModulesService")
    private UserModulesService userModulesService;
    @Resource(name = "UIService")
    private UIService UIService;


    @PostMapping("/img")
    public boolean uploadImg(MultipartFile file) {
        return UIService.uploadImg(file);
    }

    @PostMapping("/menu")
    public List<PermMenuTree> getMenuByUser(@RequestParam("userId")String userId) {
        return UIService.getMenuByUser(userId);
    }
    @ResponseBody
    @RequestMapping("/userManage")
    public void userManage(@RequestParam("id") String id){
        userModulesService.userManage(id);
    }

    @ResponseBody
    @RequestMapping("/getUserPage")
    public Page<User> getUserPage(@RequestParam("pageNumber") Long pageNumber,
                                  @RequestParam("pageSize") Long pageSize){
        QueryPage queryPage=new QueryPage();
        queryPage.setPageNumber(pageNumber);
        queryPage.setPageSize(pageSize);
        return UIService.getUserPage(queryPage);
    }

    @RequireRoles(value = {"admin"})
    @ResponseBody
    @RequestMapping("/register")
    public void register(@RequestParam("userId") String userId,
                         @RequestParam("password") String encrypt_Pwd) throws NoSuchAlgorithmException, InvalidKeySpecException {
        String privateKey = rsa.privateKey;
        RSAPrivateKey rsaPrivateKey = RSAUtils.getPrivateKey(privateKey);
        //如果数据库存储的是加密后的密文时不使用
        String Decrypt_web = RSAUtils.privateDecrypt(encrypt_Pwd, rsaPrivateKey);
        userModulesService.register(userId,Decrypt_web);
    }
}
