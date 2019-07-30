package com.itlike.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itlike.domain.Employee;
import com.itlike.domain.PageListRes;
import com.itlike.domain.QueryVo;
import com.itlike.domain.Role;
import com.itlike.mapper.EmployeeMapper;
import com.itlike.service.EmployeeService;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeMapper employeeMapper;
    @Override
    public PageListRes getEmployee(QueryVo vo) {
        //调用mapper查询员工
        Page<Object> page = PageHelper.startPage(vo.getPage(), vo.getRows());

        List<Employee> employees = employeeMapper.selectAll(vo);

        //封装成pageList
        PageListRes pageListRes = new PageListRes();
        pageListRes.setTotal(page.getTotal());
        pageListRes.setRows(employees);
        return pageListRes;
    }

    @Override
    public void saveEmployee(Employee employee) {    //保存员工

        /*把密码进行加密*/
        Md5Hash md5Hash = new Md5Hash(employee.getPassword(), employee.getUsername(), 2);
        employee.setPassword(md5Hash.toString());
        /*保存员工*/
        employeeMapper.insert(employee);
        /*保存角色关系表*/
        for(Role role:employee.getRoles()) {
            employeeMapper.insertEmployeeAndRoleRel(employee.getId(), role.getRid());
        }

    }

    @Override
    public void updateEmployee(Employee employee) {   //更新员工
        /*打破与角色之间的关系*/
        employeeMapper.deleteRoleRel(employee.getId());

        /*更新员工*/
        employeeMapper.updateByPrimaryKey(employee);

        /*重新建立与角色关系*/
        for(Role role:employee.getRoles()) {
            employeeMapper.insertEmployeeAndRoleRel(employee.getId(), role.getRid());
        }
    }

    @Override                                         //设置员工离职状态
    public void updateState(Long id) {
        employeeMapper.updateState(id);
    }

    @Override
    public Employee getEmployeeWithUserName(String username) {   //根据用户名查询有没有当前用户
        return employeeMapper.getEmployeeWithUserName(username);
    }

    @Override
    public List<String> getRolesById(Long id) {   /*根据用户id查询角色编号名称*/
        return employeeMapper.getRolesById(id);
    }

    @Override
    public List<String> getPermissionsById(Long id) {   //根据用户id查询权限 资源名称
        return employeeMapper.getPermissionsById(id);
    }


}
