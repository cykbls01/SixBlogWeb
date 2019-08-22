package com.example.demo.Entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;


@Document(collection = "Follow")
public class Follow {
    @Id
    private String id;
    private List<String> idlist;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getIdlist() {
        return idlist;
    }

    public void setIdlist(List<String> idlist) {
        this.idlist = idlist;
    }
}
