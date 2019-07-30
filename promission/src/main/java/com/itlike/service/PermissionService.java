package com.itlike.service;

import com.itlike.domain.Permission;

import java.util.List;

public interface PermissionService {
    public List<Permission> getPermissions();   //获取所有权限

    List<Permission> getPermissionByRid(Long rid); //根据角色查询对应权限
}
