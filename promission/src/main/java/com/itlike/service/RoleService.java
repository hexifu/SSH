package com.itlike.service;

import com.itlike.domain.PageListRes;
import com.itlike.domain.QueryVo;
import com.itlike.domain.Role;

import java.util.List;

public interface RoleService {
    /*查询角色列表*/
    public PageListRes getRoles(QueryVo vo);
    /*保存角色*/
    void savaRole(Role role);
    /*更新角色*/
    void updateRole(Role role);
    /*删除角色*/
    void deleteRole(Long rid);
    /*查询角色列表*/
    List<Role> roleList();
    /*根据用户id查询对应的角色*/
    List<Long> getRoleByEid(Long id);
}
