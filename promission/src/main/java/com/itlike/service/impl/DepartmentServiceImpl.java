package com.itlike.service.impl;

import com.itlike.domain.Department;
import com.itlike.mapper.DepartmentMapper;
import com.itlike.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class DepartmentServiceImpl implements DepartmentService {
    @Autowired
    private DepartmentMapper departmentMapper;
    @Override
    public List<Department> getDepartmentList() {
        return departmentMapper.selectAll();
    }
}
