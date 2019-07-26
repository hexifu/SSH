package com.hexifu.service.impl;

import com.hexifu.dao.CategoryDao;
import com.hexifu.domain.Category;
import com.hexifu.service.CategoryService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public class CategoryServiceImpl implements CategoryService {
    private CategoryDao categoryDao;

    public void setCategoryDao(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    @Override
    public void save(Category category) {
        //调用dao
        System.out.println("调用service");
        categoryDao.save(category);
    }

    @Override
    public List<Category> getAllCategory() {
        //调用dao层查询所有分类
        List<Category> list= categoryDao.getAllCategory();
        return list;
    }

    @Override
    public Category getOneCategory(Integer cid) {
        //调用dao层
        Category category = categoryDao.getOneCategory(cid);
        return category;
    }

    @Override
    public void update(Category category) {
      categoryDao.update(category);
    }

    @Override
    public void delete(Category category) {
        categoryDao.delete(category);
    }
}
