package com.example.demo.Entity;

public class CComment {

    private String content;
    private String Userid;
    private String Toid;
    private String date;
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUserid() {
        return Userid;
    }

    public void setUserid(String userid) {
        Userid = userid;
    }

    public String getToid() {
        return Toid;
    }

    public void setToid(String toid) {
        Toid = toid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
