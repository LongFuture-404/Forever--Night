package com.example.webmoduleui.dto;

import lombok.Data;

import java.util.List;

@Data
public class NavigationTree {

    private String id;
    private String navigationName;
    private String navigationKey;
    private String navigationAble;
    private String navigationUrl;
    private Integer parentId;
    private List<NavigationTree> second_navigation;
}
