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

    public List<String> getLabellist() {
        return labellist;
    }

    public void setLabellist(List<String> labellist) {
        this.labellist = labellist;
    }

    private List<String> labellist;
}
