package com.hexifu.service;

import com.hexifu.domain.Article;
import com.hexifu.domain.Category;
import com.hexifu.domain.PageBean;
import org.hibernate.criterion.DetachedCriteria;

import java.util.List;

public interface ArticleService {
     //查询所有文章业务
    List<Article> getAllArticle();
     //获取pageBean
    PageBean getPageData(DetachedCriteria detachedCriteria, Integer currPage, int pageSize);
    //删除文章
    void delete(Article article);
    //根据parentid获取分类
    List<Category> getCategory(Integer parentid);
    //
    void save(Article article);

    Article getOneArticle(Integer article_id);

    void update(Article article);
}

