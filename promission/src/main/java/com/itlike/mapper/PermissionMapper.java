package com.itlike.mapper;

import com.itlike.domain.Permission;
import java.util.List;

public interface PermissionMapper {
    int deleteByPrimaryKey(Long pid);

    int insert(Permission record);

    Permission selectByPrimaryKey(Long pid);

    List<Permission> selectAll();

    int updateByPrimaryKey(Permission record);

    List<Permission> selectPermissionByRid(Long rid);   //根据角色查询对应的权限
}