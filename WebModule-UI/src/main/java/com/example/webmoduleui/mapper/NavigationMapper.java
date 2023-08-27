package com.example.webmoduleui.mapper;

import com.example.webmoduleui.dto.NavigationTree;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NavigationMapper{
    List<NavigationTree> selectNavigation();
}
