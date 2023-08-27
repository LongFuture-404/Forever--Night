package com.example.webmoduleui.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.webmoduleui.dto.NavigationTree;
import com.example.webmoduleui.dto.PermMenuTree;
import com.example.datamoduleserve.entity.User;
import com.example.webmoduleui.dto.QueryPage;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UIService extends IService<User> {
    String verifyCodeCheck(String verifyCode,String verifyCodeTime) throws Exception;
    List<String> getVerifyCode();
    boolean uploadImg(MultipartFile file);
    List<PermMenuTree> getMenuByUser(String userId);
    List<NavigationTree> getNavigationTree();
    Page<User> getUserPage(QueryPage queryPage);
}
