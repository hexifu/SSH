package com.itlike.service.impl;

import com.itlike.domain.Permission;
import com.itlike.mapper.PermissionMapper;
import com.itlike.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PermissionServiceImpl implements PermissionService {

    //注入
    @Autowired
    private PermissionMapper permissionMapper;
    @Override
    public List<Permission> getPermissions() {
        return permissionMapper.selectAll();
    }

    @Override
    public List<Permission> getPermissionByRid(Long rid) {    //根据角色查询对应的权限


        return permissionMapper.selectPermissionByRid(rid);
    }
}
