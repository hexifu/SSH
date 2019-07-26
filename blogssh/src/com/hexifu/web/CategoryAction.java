package com.hexifu.web;

import com.hexifu.domain.Category;
import com.hexifu.service.CategoryService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.sun.org.apache.regexp.internal.RE;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import org.apache.struts2.ServletActionContext;

import java.io.IOException;
import java.util.List;

public class CategoryAction extends ActionSupport implements ModelDriven<Category> {
    private Category category = new Category();
    @Override
    public Category getModel() {
        return category;
    }


    //注入service
    private CategoryService categoryService;

    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    public String add(){
        //调用业务层
        categoryService.save(category);
        return "listAction";
    }

    public String list(){
        //调用业务层，查询所有分类
        List<Category> list = categoryService.getAllCategory();
        //把数据存储在值栈中
        ActionContext.getContext().getValueStack().set("categorylist",list);
        return "list";
    }

    public String updateUI() throws IOException {
        //调用业务层
        Category category2 = categoryService.getOneCategory(category.getCid());
        //把数据给页面
        //以json(数据格式)响应给页面
        JSONArray jsonArray = JSONArray.fromObject(category2, new JsonConfig());
        //响应给页面
        ServletActionContext.getResponse().setContentType("text/html:charset=utf-8");
        ServletActionContext.getResponse().getWriter().println(jsonArray.toString());
        return null;
    }

    public String update(){
        //调用业务层
        categoryService.update(category);
        return "listAction";
    }

    public String delete(){
        categoryService.delete(category);
        return "listAction";
    }
}
