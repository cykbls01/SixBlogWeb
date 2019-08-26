package com.example.demo.Controller;

import com.example.demo.Dao.BlogDao;
import com.example.demo.Dao.CommentDao;
import com.example.demo.Dao.UserDao;
import com.example.demo.Entity.Blog;
import com.example.demo.Entity.Comment;
import com.example.demo.Entity.User;
import com.example.demo.Repository.BlogRepository;
import com.example.demo.Repository.CommentRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Tools.Time;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.util.*;

@Controller
public class BlogController {

    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentRepository commentRepository;

    @PostMapping(value ="/Blog/add")
    public String add(@RequestParam("label")String label, Blog blog, HttpSession session){

        blog.setDate(Time.getTime());
        blog.setLabel(Arrays.asList(label.split(" ")));
        User user=(User)session.getAttribute("user");
        blog.setAuthorid(user.getId());
        blog.setAuthorname(user.getUsername());
        blog.setNumber(0);
        blogRepository.save(blog);
        //System.out.println(blog.getContent());
        return "redirect:/user/info";
    }

    @GetMapping(value ="/Blog/delete/{id}")
    public String delete(@PathVariable String id){

        blogRepository.deleteById(id);
        return "bingo";
    }

    @PostMapping(value ="/Blog/modify")
    public String modify(Blog blog){

        blog.setDate(Time.getTime());
        blog.setNumber(0);
        blogRepository.save(blog);
        return "modify";
    }


    @RequestMapping("/hello")
    public String helloWorld(){




        return "Bingo";
    }


    @GetMapping(value ="/blog/get/{id}")
    public String getbyid(@PathVariable String id, Model model,HttpSession session)
    {
        Blog blog=blogRepository.findById(id).get();
        System.out.println(blog.getContent());
        User author= UserDao.findbyusername(blog.getAuthorname(),userRepository);
        model.addAttribute("blog",blog);
        model.addAttribute("author",author);
        model.addAttribute("yyyymm",blog.getDate().substring(0,4)+blog.getDate().substring(5,7));
        model.addAttribute("dd",blog.getDate().substring(8,10));
        session.setAttribute("blog",blog);
        session.setAttribute("author",author);
        session.setAttribute("yyyymm",blog.getDate().substring(0,4)+blog.getDate().substring(5,7));
        session.setAttribute("dd",blog.getDate().substring(8,10));
        List<Comment> commentList= CommentDao.findbyblogid(blog.getId(),commentRepository);
        model.addAttribute("comments",commentList);


        return "Blog";
    }

    @PostMapping(value = "/blog/{name}")
    public  String search(@PathVariable String name, Model model)
    {
        List<Blog> blogList= BlogDao.search(name,blogRepository);
        model.addAttribute("blogs",blogList);
        for(int i=0;i<blogList.size();i++)
        System.out.println(blogList.get(i).getTheme());
        return "Bingo";
    }

    @GetMapping(value = "/blog/123")
    public  String search123()
    {
        List<Blog> blogList= BlogDao.search("Java",blogRepository);
        //model.addAttribute("blogs",blogList);
        for(int i=0;i<blogList.size();i++)
            System.out.println(blogList.get(i).getTheme());
        return "redirect:/hello";
    }

    @GetMapping(value ="/blog/label/{label}")
    public String getbylabel(@PathVariable String label, Model model) throws ParseException {
        List<Blog> blogList= BlogDao.findbylabel(label,blogRepository);
        model.addAttribute("blogs",blogList);
        return "index";
    }

    @PostMapping(value = "/blog/search")
    public String search(@RequestParam("name")String name,@RequestParam("button")String label,Model model)
    {
        if(label.equals("博文"))
        {
            List<Blog> blogList=BlogDao.search(name,blogRepository);
            System.out.println(label+name);
            model.addAttribute("blogs",blogList);
            return "index";


        }
        else
        {
            List<User> userList=UserDao.searchbyusername(name,userRepository);
            System.out.println(name);
            model.addAttribute("users",userList);
            return "SearchUser";
        }




    }
    @GetMapping(value = "/")
    public String recommon(Model model,HttpSession session) throws ParseException {
        List<Blog> blogList=BlogDao.recommond(blogRepository);



        model.addAttribute("blogs",blogList);
        return "index";
    }

    @GetMapping(value = "/index.html")
    public String recommon1(Model model) throws ParseException {
        List<Blog> blogList=BlogDao.recommond(blogRepository);
        model.addAttribute("blogs",blogList);
        return "index";
    }





}
