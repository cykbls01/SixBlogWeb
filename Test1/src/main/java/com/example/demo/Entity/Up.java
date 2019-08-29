package com.example.demo.Entity;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "Up")
public class Up {
    @Id
    private String id;
    private String Blogid;
    private List<String> userid;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBlogid() {
        return Blogid;
    }

    public void setBlogid(String blodid) {
        this.Blogid = blodid;
    }

    public List<String> getUserid() {
        return userid;
    }

    public void setUserid(List<String> userid) {
        this.userid = userid;
    }
}
