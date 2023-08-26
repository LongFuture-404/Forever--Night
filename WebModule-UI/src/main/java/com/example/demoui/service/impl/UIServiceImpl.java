package com.example.demoui.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demoui.dto.NavigationTree;
import com.example.demoui.dto.PermMenuTree;
import com.example.demoserve.entity.User;
import com.example.demoui.dto.QueryPage;
import com.example.demoui.mapper.NavigationMapper;
import com.example.demoui.mapper.UserMapper;
import com.example.demoui.constants.PropertyConstant;
import com.example.demoui.service.UIService;
import jakarta.annotation.Resource;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.io.File;
import java.util.*;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.DigestUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Service("UIService")
public class UIServiceImpl extends ServiceImpl<UserMapper, User> implements UIService {
    private static final Integer TYPE_MODULE = 1;
    private static final Integer TYPE_BUTTON = 3;

    @Resource
    private DefaultKaptcha kaptcha;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private PropertyConstant constant;
    @Resource
    private UserMapper userMapper;
    @Resource
    private NavigationMapper navigationMapper;
    @Override
    public String verifyCodeCheck(String verifyCode,String kaptchaOwner){
        //验证码的验证逻辑:
        //获取前端的verifyCode，将verifyCode转换成md5，以md5值为key到redis查询，如果redis中存在该值，则认为认证成功，否则认证失败
        verifyCode = constant.getVerifyPrefix() + kaptchaOwner + verifyCode;
        String md5Code = DigestUtils.md5DigestAsHex(verifyCode.getBytes());
        String verifyCodeValue = stringRedisTemplate.opsForValue().get(md5Code);
        if (ObjectUtils.isEmpty(verifyCodeValue)) {
            return "error";
        }
        return "success";
//        //把username和password传给数据库，由数据库做查询验证
//        //mybatis-plus提供了条件构造器QueryWrapper，一般用来做单表查询
//        //sql：select * from user where username=? and password=?
//        QueryWrapper<InputUser> w = new QueryWrapper<>();
//        //w.eq方法相当于sql的username=?条件
//        //方法的第一个参数是数据表中的字段名称
//        w.eq("userid", loginUser.getUserid());
//        w.eq("password", loginUser.getPassword());
//
//        //getOne表示从数据库查询一条数据,如果数据库查询结果有多条数据，则抛异常
//        InputUser user=this.getOne(w);
    }

    /**
     * 1.生成验证码文本，存入redis
     * 2.生成验证码图片，转换成base64编码
     * 3.把编码返回给controller
     *
     * @return
     */
    @Override
    public List<String> getVerifyCode() {
        List<String> VerifyCode=new ArrayList<>();
        String kaptchaOwner = UUID.randomUUID().toString();
        //生成验证码文本
        String text = kaptcha.createText();
        //把文本存入redis
        String key = constant.getVerifyPrefix() + kaptchaOwner + text;
        String md = DigestUtils.md5DigestAsHex(key.getBytes());
        stringRedisTemplate.opsForValue().set(md, text, 10, TimeUnit.MINUTES);
        //生成验证码图片，把图片转成base64编码
        BufferedImage image = kaptcha.createImage(text);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "jpg", bos);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String VerifyCodeData="data:image/jpeg;base64," + Base64.getEncoder().encodeToString(bos.toByteArray());
        VerifyCode.add(kaptchaOwner);
        VerifyCode.add(VerifyCodeData);
        return VerifyCode;
    }
    @Override
    public boolean uploadImg(MultipartFile file) {
        String descPath = constant.getUploadPath() + file.getOriginalFilename();
        File descFile = new File(descPath);
        try {
            file.transferTo(descFile);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    @Override
    public List<PermMenuTree> getMenuByUser(String userId) {
        Map<String, Object> param = new HashMap<>();
        param.put("userId", userId);
        param.put("permissionType", TYPE_MODULE);
        List<PermMenuTree> permMenuTrees = userMapper.selectMenuByUser(param);
//        System.out.println("处理前： "+permMenuTrees);
        //TODO 对list进行封装
        // 1.过滤掉permissionType=3的数据
        // 2.如果children列表不为空，则设置hasChildren为true

        for (PermMenuTree tree : permMenuTrees) {
            List<PermMenuTree> children = tree.getChildren();
            List<PermMenuTree> newChildren = new ArrayList<>();
            for (PermMenuTree t : children) {
                if (t.getPermissionType() < TYPE_BUTTON) {
                    newChildren.add(t);
                }
            }
            if (!CollectionUtils.isEmpty(newChildren)) {
                tree.setHasChildren(true);
                tree.setChildren(newChildren);
            }
        }
//        System.out.println("处理后： "+permMenuTrees);
        return permMenuTrees;
    }
    @Override
    public List<NavigationTree> getNavigationTree(){
        return navigationMapper.selectNavigation();
    }
    @Override
    public Page<User> getUserPage(QueryPage queryPage) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(queryPage.getSelectName()), "userName", queryPage.getSelectName());
        Page<User> page = new Page<>();
        //设置当前页
        page.setCurrent(queryPage.getPageNumber());
        //设置每页显示多少条数据
        page.setSize(queryPage.getPageSize());

//        return userMapper.selectPage(page, queryWrapper);
        return this.page(page, queryWrapper);
    }
}
