package com.example.demo.Controller;

import com.example.demo.Entity.Blog;
import com.example.demo.Repository.BlogRepository;
import com.example.demo.Tools.Time;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
public class BlogController {

    @Autowired
    private BlogRepository blogRepository;


    @PostMapping(value ="/Blog/add")
    public String add(Blog blog){
        blog.setDate(Time.getTime());
        blogRepository.save(blog);
        return "add";
    }

    @PostMapping(value ="/Blog/delete")
    public String delete(@RequestParam("email")String id){

        blogRepository.deleteById(id);
        return "delete";
    }

    @PostMapping(value ="/Blog/modify")
    public String modify(Blog blog){

        blog.setDate(Time.getTime());
        blogRepository.save(blog);
        return "modify";
    }

    @ResponseBody
    @RequestMapping("/hello")
    public String helloWorld(){

        Blog blog;
        blog=blogRepository.findById("5d5e3ef6977b3935ac12a6bd").get();
        System.out.println(blog.getLabel().get(1));


        return "success";
    }



}
