package com.itlike.service;

import com.itlike.domain.AjaxRes;
import com.itlike.domain.Menu;
import com.itlike.domain.PageListRes;
import com.itlike.domain.QueryVo;

import java.util.List;

public interface MenuService {
    PageListRes getMenuList(QueryVo vo);

    /*查询所有菜单*/
    List<Menu> parentList();
    /*保存菜单*/
    void saveMenu(Menu menu);
    /*更新菜单*/
    AjaxRes updateMenu(Menu menu);
    /*删除菜单*/
    AjaxRes deleteMenu(Long id);
    /*获取树形菜单*/
    List<Menu> getTreeData();
}
