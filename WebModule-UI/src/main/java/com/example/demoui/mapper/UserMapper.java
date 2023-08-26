package com.example.demoui.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demoui.dto.PermMenuTree;
import com.example.demoserve.entity.User;
import com.example.demoui.dto.User_RoleInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    List<PermMenuTree> selectMenuByUser(Map<String, Object> map);
    List<User_RoleInfo> selectRoleByUserId(String userId);
}
