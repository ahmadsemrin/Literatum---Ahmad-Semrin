package webappdesign.model;

import java.io.Serializable;
import java.util.Date;

public class Article implements Serializable {
    private String articleName;
    private String articleSection;
    private Date publishDate;
    private double price;

    public void setArticleName(String articleName) {
        this.articleName = articleName;
    }

    public void setArticleSection(String articleSection) {
        this.articleSection = articleSection;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getArticleName() {
        return articleName;
    }

    public String getArticleSection() {
        return articleSection;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public double getPrice() {
        return price;
    }
}
