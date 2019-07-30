package com.itlike.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itlike.domain.*;
import com.itlike.mapper.MenuMapper;
import com.itlike.service.MenuService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuMapper menuMapper;
    @Override
    public PageListRes getMenuList(QueryVo vo) {
        //调用mapper查询菜单
        Page<Object> page = PageHelper.startPage(vo.getPage(), vo.getRows());

        List<Menu> menus = menuMapper.selectAll();

        //封装成pageList
        PageListRes pageListRes = new PageListRes();
        pageListRes.setTotal(page.getTotal());
        pageListRes.setRows(menus);
        return pageListRes;
    }

    @Override
    public List<Menu> parentList() {        //查询所有菜单   父菜单下拉列表选项
        return menuMapper.selectAll();
    }

    @Override
    public void saveMenu(Menu menu) {     //保存菜单
        menuMapper.saveMenu(menu);
    }

    @Override
    public AjaxRes updateMenu(Menu menu) {
        AjaxRes ajaxRes = new AjaxRes();
        /*判断选择的父菜单是不是自己的子菜单*/
        /*先取出当前自己在下拉列表选择的父菜单 的 id */
        Long id = menu.getParent().getId();
        /*查询该id对应的menu*/
        Long parent_id=menuMapper.selectParentId(id);

        if(menu.getId()==parent_id){
            ajaxRes.setMsg("不能设置自己的子菜单为自己的父菜单");
            ajaxRes.setSuccess(false);
            return ajaxRes;
        }

        try{
            menuMapper.updateByPrimaryKey(menu);
            ajaxRes.setMsg("保存成功");
            ajaxRes.setSuccess(true);
        }catch (Exception e){
            ajaxRes.setSuccess(false);
            ajaxRes.setMsg("保存失败");
        }
        return ajaxRes;
    }

    @Override
    public AjaxRes deleteMenu(Long id) {   //删除菜单


        AjaxRes ajaxRes = new AjaxRes();
        try{
            /*打破菜单关系   因为有级联关系*/
            menuMapper.updateMenuRel(id);
            /*删除菜单*/
            menuMapper.deleteByPrimaryKey(id);
            ajaxRes.setMsg("删除成功");
            ajaxRes.setSuccess(true);
        }catch (Exception e){
            ajaxRes.setSuccess(false);
            ajaxRes.setMsg("删除失败");
        }
        return ajaxRes;
    }

    @Override
    public List<Menu> getTreeData() {     /*获取树形菜单*/
        List<Menu> treeData = menuMapper.getTreeData();

        /*判断当前用户有没有对应的权限
         * 如果没有就从集合中移出
         * */
        /*获取用户 判断用户是不是管理员 是管理员就不需要做判断*/
        Subject subject = SecurityUtils.getSubject();
        /*当前的用户*/
        Employee employee = (Employee) subject.getPrincipal();
        if(!employee.getAdmin()){
            /*做校验权限*/
            checkPermission(treeData);
        }

        return treeData;
    }

    public void checkPermission(List<Menu> menus){
        //获取主体
        Subject subject = SecurityUtils.getSubject();

        //遍历所有的菜单及子菜单
        Iterator<Menu> iterator = menus.iterator();
        while (iterator.hasNext()){
            Menu menu = iterator.next();
            if(menu.getPermission()!=null){
                //判断当前menu是否有权限对象 如果没有 当前遍历的菜单从集合中移出
                String presource = menu.getPermission().getPresource();
                if(!subject.isPermitted(presource)){
                    //当前遍历的菜单从集合中移出
                    iterator.remove();
                    continue;
                }
            }
            //判断是否友子菜单 有子菜单也要做权限校验
            if(menu.getChildren().size()>0){
                checkPermission(menu.getChildren());
            }
        }

    }
}
