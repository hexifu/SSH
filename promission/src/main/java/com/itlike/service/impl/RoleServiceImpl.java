package com.itlike.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itlike.domain.PageListRes;
import com.itlike.domain.Permission;
import com.itlike.domain.QueryVo;
import com.itlike.domain.Role;
import com.itlike.mapper.RoleMapper;
import com.itlike.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {
    //注入mapper
    @Autowired
    private RoleMapper roleMapper;
    @Override
    public PageListRes getRoles(QueryVo vo) {      //查询角色列表

        Page<Object> page = PageHelper.startPage(vo.getPage(), vo.getRows());  //分页
        List<Role> roles = roleMapper.selectAll();  //调用mapper查询数据

        //封装成pageList
        PageListRes pageListRes = new PageListRes();
        pageListRes.setTotal(page.getTotal());
        pageListRes.setRows(roles);
        return pageListRes;

    }

    @Override
    public void savaRole(Role role) {       //保存角色
        /*保存角色*/
      roleMapper.insert(role);
      /*保存角色与权限之间关系*/
        for(Permission permission:role.getPermissions()){
            roleMapper.insertRoleAndPermissionRel(role.getRid(),permission.getPid());
        }
    }

    @Override
    public void updateRole(Role role) {    //更新角色
        /*打破角色与权限之前的关系*/
        roleMapper.deletePermissionRel(role.getRid());
        /*更新角色*/
        roleMapper.updateByPrimaryKey(role);
        /*重新保存角色与权限的关系*/
        for(Permission permission:role.getPermissions()){
            roleMapper.insertRoleAndPermissionRel(role.getRid(),permission.getPid());
        }

    }

    @Override
    public void deleteRole(Long rid) {     //删除角色
       /*1.删除关联的权限*/
        roleMapper.deletePermissionRel(rid);
        /*2.删除对应的角色*/
        roleMapper.deleteByPrimaryKey(rid);
    }

    @Override
    public List<Role> roleList() {
        return roleMapper.selectAll();
    }

    @Override
    public List<Long> getRoleByEid(Long id) {  /*根据用户id查询对应的角色*/
        return roleMapper.getRoleWithId(id);
    }
}
