package com.itlike.web;

import com.itlike.domain.AjaxRes;
import com.itlike.domain.PageListRes;
import com.itlike.domain.QueryVo;
import com.itlike.domain.Role;
import com.itlike.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class RoleController {


    /*注入业务层*/
    @Autowired
    private RoleService roleService;


    @RequestMapping("/role")
    public String employee(){
        return "role";
    }

    /*角色列表请求*/
    @RequestMapping("/getRoles")
    @ResponseBody
    public PageListRes getRoles(QueryVo vo){
        /*调用业务层，查询角色列表*/
        PageListRes roles = roleService.getRoles(vo);
        return roles;
    }

    /*接收 把保存角色请求地址*/
    @RequestMapping("/saveRole")
    @ResponseBody
    public AjaxRes saveRole(Role role){

        AjaxRes ajaxRes = new AjaxRes();

        try{
            /*调用业务层,保存角色*/
            roleService.savaRole(role);
            ajaxRes.setMsg("保存成功");
            ajaxRes.setSuccess(true);
        }catch (Exception e){
            ajaxRes.setSuccess(false);
            ajaxRes.setMsg("保存失败");

        }
        return ajaxRes;
    }

    @RequestMapping("/updateRole")
    @ResponseBody
    public AjaxRes updateRole(Role role){

        AjaxRes ajaxRes = new AjaxRes();

        try{
            /*调用业务层,更新角色*/
            roleService.updateRole(role);
            ajaxRes.setMsg("编辑成功");
            ajaxRes.setSuccess(true);
        }catch (Exception e){
            ajaxRes.setSuccess(false);
            ajaxRes.setMsg("编辑失败");
        }
        return ajaxRes;
    }

    @RequestMapping("/deleteRole")
    @ResponseBody
    public AjaxRes deleteRole(Long rid){
        AjaxRes ajaxRes = new AjaxRes();

        try{
            /*调用业务层,删除角色*/
            roleService.deleteRole(rid);
            ajaxRes.setMsg("删除成功");
            ajaxRes.setSuccess(true);
        }catch (Exception e){
            ajaxRes.setSuccess(false);
            ajaxRes.setMsg("删除失败");
        }
        return ajaxRes;
    }

    /*选择角色列表请求*/
    @RequestMapping("/roleList")
    @ResponseBody
    public List<Role> roleList(){
        return roleService.roleList();
    }

    /*根据用户id查询对应角色*/
    @RequestMapping("/getRoleByEid")
    @ResponseBody
    public List<Long>getRoleByEid(Long id){
        /*查询对应的角色*/
        return roleService.getRoleByEid(id);
    }
}
