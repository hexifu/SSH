package com.hexifu.web;

import com.hexifu.domain.Article;
import com.hexifu.domain.Category;
import com.hexifu.domain.PageBean;
import com.hexifu.service.ArticleService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class ArticleAction extends ActionSupport implements ModelDriven<Article> {
    private Article article = new Article();
    @Override
    public Article getModel() {
        return article;
    }
    //注入
    private ArticleService articleService;

    public void setArticleService(ArticleService articleService) {
        this.articleService = articleService;
    }

    public String list(){
        //调用业务层
        List<Article> allArticle = articleService.getAllArticle();
        //把数据存储在值栈中。转发到jsp
        ActionContext.getContext().getValueStack().set("allArticle",allArticle);
        return "list";
    }

//    获取分页数据
    private Integer currPage = 1;
    //搜索的关键字
    private String keyWord;

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public void setCurrPage(Integer currPage) {
        this.currPage = currPage;
    }

    public String pageList(){
        System.out.println(keyWord);
        //离线查询条件
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Article.class);
        //设置查询条件
        if (keyWord!=null){
            //添加条件
            detachedCriteria.add(Restrictions.like("article_title","%"+keyWord+"%"));
        }

        //调用业务层
        PageBean pageBean = articleService.getPageData(detachedCriteria,currPage,5);
        ActionContext.getContext().getValueStack().push(pageBean);//数据存到值栈当中
        return "list";
    }

    //删除
    public String delete(){
        //调用业务层
        Article article2 = new Article();
        article2.setArticle_id(article2.getArticle_id());
        articleService.delete(article);
        return "listres";
    }

    private Integer parentid;
    public void setParentid(Integer parentid) {
        this.parentid = parentid;
    }
    public String getCategory() throws IOException {
        System.out.println(parentid);
        //根据parentid查询分类
        List<Category> list = articleService.getCategory(parentid);
        JSONArray jsonArray = JSONArray.fromObject(list, new JsonConfig());
        //响应给页面
        ServletActionContext.getResponse().setContentType("text/html:charset=utf-8");
        ServletActionContext.getResponse().getWriter().println(jsonArray.toString());
        return null;
    }

    //添加文章
    //文件上传提供的三个属性:
    private String uploadFileName; // 文件名称
    private File upload; // 上传文件
    private String uploadContentType; // 文件类型
    public void setUploadFileName(String uploadFileName) {
        this.uploadFileName = uploadFileName;
    }
    public void setUpload(File upload) {
        this.upload = upload;
    }
    public void setUploadContentType(String uploadContentType) {
        this.uploadContentType = uploadContentType;
    }
    public String add() throws IOException {

        if(upload != null){
            //上传文件
            //随机生成文件名称
            //1.获取文件扩展名  ssh.jpg
            int index = uploadFileName.lastIndexOf(".");
            String etx = uploadFileName.substring(index);
            //2.随机生成文件名  拼接扩展名
            String uuid = UUID.randomUUID().toString();
            String uuidFileName = uuid.replace("-", "") + etx;
            System.out.println(uuidFileName);

            //确定上传的路径
            String path = ServletActionContext.getServletContext().getRealPath("/upload");
            File file = new File(path);
            if(!file.exists()){
                file.mkdirs();
            }
            //拼接新文件路径
            File desFile = new File(path + "/" + uuidFileName);
            //文件上传
            FileUtils.copyFile(upload,desFile);
            //设置图片
            article.setArticle_pic(uuidFileName);
        }
        //设置当前时间
        article.setArticle_time(new Date().getTime());

        System.out.println(article);

        //调用业务层，保存到数据库
        articleService.save(article);
        return "listres";
}

    public String edit(){
        //从数据库中获取当前文章
        Article resarticle = articleService.getOneArticle(article.getArticle_id());
        //吧查询的结果放在值栈中
        ActionContext.getContext().getValueStack().push(resarticle);
        return "edit";
    }

    public String update() throws IOException {
        if(upload != null){
            //确定上传的路径
            String path = ServletActionContext.getServletContext().getRealPath("/upload");
            String picname = article.getArticle_pic();
            //删除原有图片
            if(picname != null ||"".equals(picname)){
                File file = new File(path + picname);
                file.delete();
            }
            int index = uploadFileName.lastIndexOf(".");
            String etx = uploadFileName.substring(index);
            //2.随机生成文件名  拼接扩展名
            String uuid = UUID.randomUUID().toString();
            String uuidFileName = uuid.replace("-", "") + etx;

            File file = new File(path);
            if(!file.exists()){
                file.mkdirs();
            }
            //拼接新文件路径
            File desFile = new File(path + "/" + uuidFileName);
            //文件上传
            FileUtils.copyFile(upload,desFile);
            //设置图片
            article.setArticle_pic(uuidFileName);
        }
        //设置时间
        article.setArticle_time(System.currentTimeMillis());
        //调用业务更新文章
        articleService.update(article);
        return "listres";
    }


}
