package com.example.demo.Entity;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

@Document(collection = "Blog")
public class Blog {
    @Id
    private String id;
    private String theme;
    private String content;
    private String date;
    private String Authorid;
    private String Authorname;
    private int number;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getAuthorname() {
        return Authorname;
    }

    public void setAuthorname(String authorname) {
        Authorname = authorname;
    }



    private List<String> label;


    public List<String> getLabel() {
        return label;
    }

    public void setLabel(List<String> label) {
        this.label = label;
    }





    public String getAuthorid() {
        return Authorid;
    }

    public void setAuthorid(String authorid) {
        Authorid = authorid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


}
