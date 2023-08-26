package com.example.demoui.mapper;

import com.example.demoui.dto.NavigationTree;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NavigationMapper{
    List<NavigationTree> selectNavigation();
}
