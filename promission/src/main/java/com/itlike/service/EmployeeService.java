package com.itlike.service;

import com.itlike.domain.Employee;
import com.itlike.domain.PageListRes;
import com.itlike.domain.QueryVo;

import java.util.List;

public interface EmployeeService {


    //查询员工
    public PageListRes getEmployee(QueryVo vo);
    //保存员工
    void saveEmployee(Employee employee);
    //更新员工
    void updateEmployee(Employee employee);
    //设置员工离职
    void updateState(Long id);
    //根据用户名查询有没有当前用户
    Employee getEmployeeWithUserName(String username);
    //根据用户id查询角色编号名称
    List<String> getRolesById(Long id);
    //根据用户id查询权限 资源名称
    List<String> getPermissionsById(Long id);
}
