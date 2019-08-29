package com.example.demo.Entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "Comment")
public class Comment {
    @Id
    private String id;
    private String Blogid;
    private String Authorid;
    private String Authorname;
    private String AuthorImageid;
    private String content;
    private String date;
    //private List<CComment> commentList;


    public String getAuthorImageid() {
        return AuthorImageid;
    }

    public void setAuthorImageid(String authorImageid) {
        AuthorImageid = authorImageid;
    }

    public String getAuthorname() {
        return Authorname;
    }

    public void setAuthorname(String authorname) {
        Authorname = authorname;
    }

    //public List<CComment> getCommentList() {
        //return commentList;
    //}

    //public void setCommentList(List<CComment> commentList) {
        //this.commentList = commentList;
    //}

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }




    public String getAuthorid() {
        return Authorid;
    }

    public void setAuthorid(String userid) {
        Authorid = userid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }




    public String getBlogid() {
        return Blogid;
    }

    public void setBlogid(String blogid) {
        Blogid = blogid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
