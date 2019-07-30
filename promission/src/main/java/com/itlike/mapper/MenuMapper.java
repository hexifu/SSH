package com.itlike.mapper;

import com.itlike.domain.Menu;
import java.util.List;

public interface MenuMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Menu record);

    Menu selectByPrimaryKey(Long id);

    List<Menu> selectAll();

    int updateByPrimaryKey(Menu record);
    /*保存菜单*/
    void saveMenu(Menu menu);

    Long selectParentId(Long id);
    /*跟新菜单关系*/
    void updateMenuRel(Long id);
    /*获取树形菜单*/
    List<Menu> getTreeData();
}