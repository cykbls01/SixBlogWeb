package com.example.demo.Entity;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Up")
public class Up {
    @Id
    private String id;
    private String blodid;
    private String userid;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBlodid() {
        return blodid;
    }

    public void setBlodid(String blodid) {
        this.blodid = blodid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }








}
