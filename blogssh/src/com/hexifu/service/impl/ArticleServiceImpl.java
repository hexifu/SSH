package com.hexifu.service.impl;

import com.hexifu.dao.ArticleDao;
import com.hexifu.domain.Article;
import com.hexifu.domain.Category;
import com.hexifu.domain.PageBean;
import com.hexifu.service.ArticleService;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Transactional
public class ArticleServiceImpl implements ArticleService {
    //注入
    private ArticleDao articleDao;
    public void setArticleDao(ArticleDao articleDao) {
        this.articleDao = articleDao;
    }

    @Override
    public List<Article> getAllArticle() {
        //调用dao
        List<Article> allArticle = articleDao.getAllArticle();
        return allArticle;
    }

    @Override
    public PageBean getPageData(DetachedCriteria detachedCriteria, Integer currPage, int pageSize) {
        PageBean<Article> pageBean = new PageBean<>();
        //设置当前页
        pageBean.setCurrentPage(currPage);
        //设置当前一页有多少条数据
        pageBean.setPageSize(pageSize);
        //获取总记录数
        //从数据库中查询总记录数
        Integer totalCount = articleDao.getTotalCount(detachedCriteria);
        pageBean.setTotalCount(totalCount);
        //设置总页数
        pageBean.setTotalPage(pageBean.getTotalPage());
        //设置数据  当前页数据
        //查询数据库
        List<Article> list = articleDao.getPageData(detachedCriteria,pageBean.getIndex(),pageBean.getPageSize());
        //计算
        pageBean.setList(list);


        return pageBean;
    }

    @Override
    public void delete(Article article) {
        articleDao.delete(article);
    }

    @Override
    public List<Category> getCategory(Integer parentid) {
        //调用dao
        List<Category> list= articleDao.getCategory(parentid);
        return list;
    }

    @Override
    public void save(Article article) {
        articleDao.save(article);
    }

    @Override
    public Article getOneArticle(Integer article_id) {
        Article resarticle = articleDao.getOneArticle(article_id);
        return resarticle;
    }

    @Override
    public void update(Article article) {
        articleDao.update(article);
    }
}
