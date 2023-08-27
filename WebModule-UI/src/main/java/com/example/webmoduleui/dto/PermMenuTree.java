package com.example.webmoduleui.dto;

import lombok.Data;

import java.util.List;

/**
 * @author sdx2009
 * @date 2023/4/13 14:36
 */
@Data
public class PermMenuTree {

    private String id;
    private String permissionName;
    private Integer parentId;
    private String permissionKey;
    private Integer permissionType;
    private String permissionUrl;
    private boolean hasChildren;
    private List<PermMenuTree> children;
}
