package com.hexifu.domain;

public class Article {
    private Integer article_id;
    private String article_title;
    private String article_content;
    private Long article_time;
    private String article_pic;
    private String article_desc;
    private Category category;

    public Integer getArticle_id() {
        return article_id;
    }

    public void setArticle_id(Integer article_id) {
        this.article_id = article_id;
    }

    public String getArticle_title() {
        return article_title;
    }

    public void setArticle_title(String article_title) {
        this.article_title = article_title;
    }

    public String getArticle_content() {
        return article_content;
    }

    public void setArticle_content(String article_content) {
        this.article_content = article_content;
    }

    public Long getArticle_time() {
        return article_time;
    }

    public void setArticle_time(Long article_time) {
        this.article_time = article_time;
    }

    public String getArticle_pic() {
        return article_pic;
    }

    public void setArticle_pic(String article_pic) {
        this.article_pic = article_pic;
    }

    public String getArticle_desc() {
        return article_desc;
    }

    public void setArticle_desc(String article_desc) {
        this.article_desc = article_desc;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Article{" +
                "article_id=" + article_id +
                ", article_title='" + article_title + '\'' +
                ", article_content='" + article_content + '\'' +
                ", article_time=" + article_time +
                ", article_pic='" + article_pic + '\'' +
                ", article_desc='" + article_desc + '\'' +
                ", category=" + category +
                '}';
    }
}
