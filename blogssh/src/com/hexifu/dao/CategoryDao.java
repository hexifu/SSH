package com.hexifu.dao;

import com.hexifu.domain.Category;

import java.util.List;

public interface CategoryDao {

    public void save(Category category);//保存分类

    List<Category> getAllCategory(); //获取所有分类

    Category getOneCategory(Integer cid);//根据id在数据库中查询分类

    void update(Category category);//修改分类

    void delete(Category category);//删除分类
}
