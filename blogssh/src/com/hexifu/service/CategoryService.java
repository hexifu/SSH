package com.hexifu.service;

import com.hexifu.domain.Category;

import java.util.List;

public interface CategoryService {
    public void save(Category category);
    //获取所有分类信息
    List<Category> getAllCategory();
    //根据id查询分类信息
    Category getOneCategory(Integer cid);
    //修改分类
    void update(Category category);
    //删除分类
    void delete(Category category);
}
