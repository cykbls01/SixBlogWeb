package com.example.demo.Controller;

import com.example.demo.Dao.*;
import com.example.demo.Entity.*;
import com.example.demo.Repository.*;
import com.example.demo.Tools.Time;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.http.HttpSession;
import java.io.IOException;
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
    @Autowired
    private FollowRepository followRepository;
    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private UpRepository upRepository;

    @PostMapping(value ="/Blog/add")
    public String add(@RequestParam(value = "image") MultipartFile file,@RequestParam("label")String[] label, Blog blog, HttpSession session){

        blog.setDate(Time.getTime());
        if(label!=null)
        blog.setLabel(Arrays.asList(label));

        User user=(User)session.getAttribute("user");
        blog.setAuthorid(user.getId());
        blog.setAuthorname(user.getUsername());
        blog.setNumber(0);
        String fileName = file.getOriginalFilename();
        if(fileName.length()!=0){
        Uploadfile uploadFile = new Uploadfile();
        try {

            uploadFile.setName(fileName);
            uploadFile.setCreatedTime(Time.getTime());
            uploadFile.setContent(new Binary(file.getBytes()));
            uploadFile.setContentType(file.getContentType());
            uploadFile.setSize(file.getSize());
            fileRepository.save(uploadFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        uploadFile= FileDao.find(uploadFile,fileRepository);
        blog.setFileid(uploadFile.getId());}
        blogRepository.save(blog);

        return "redirect:/user/info";
    }//写博客+上传资源

    @GetMapping(value ="/Blog/delete/{id}")
    public String delete(@PathVariable String id){

        Blog blog=blogRepository.findById(id).get();
        Up up=UpDao.findbyblogid(id,upRepository);
        if(up!=null) upRepository.delete(up);
        fileRepository.deleteById(blog.getFileid());
        CommentDao.deletebyblogid(id,commentRepository);
        blogRepository.deleteById(id);


        return "redirect:/user/info";
    }//删除博客

    @PostMapping(value ="/Blog/modify")
    public String modify(Blog blog){

        blog.setDate(Time.getTime());
        blog.setNumber(0);
        blogRepository.save(blog);
        return "modify";
    }//修改博客


    @GetMapping(value ="/blog/get/{id}")
    public String getbyid(@PathVariable String id, Model model,HttpSession session) throws ParseException {
        Blog blog=blogRepository.findById(id).get();
        User user=(User)session.getAttribute("user");
        if(user==null)
        {
            session.setAttribute("like",false);
        }
        else {
            Up up = UpDao.findbyblogid(id, upRepository);
            if (up == null) {
                session.setAttribute("like", false);
                System.out.println(1);
            } else if (UpDao.islike(user.getId(), up, upRepository)) {
                session.setAttribute("like", true);
                System.out.println(2);
            } else session.setAttribute("like", false);
        }



        User author= UserDao.findbyusername(blog.getAuthorname(),userRepository);
        model.addAttribute("blog",blog);
        model.addAttribute("author",author);
        model.addAttribute("yyyymm",blog.getDate().substring(0,4)+blog.getDate().substring(5,7));
        model.addAttribute("dd",blog.getDate().substring(8,10));



        model.addAttribute("lastupdate",BlogDao.findbyAuthorid(author.getId(),blogRepository).get(0));
        if(blog.getFileid().length()==0) model.addAttribute("isfile","false");
        else model.addAttribute("isfile","true");
        session.setAttribute("blog",blog);

        session.setAttribute("author",author);
        session.setAttribute("yyyymm",blog.getDate().substring(0,4)+blog.getDate().substring(5,7));
        session.setAttribute("dd",blog.getDate().substring(8,10));
        List<Comment> commentList= CommentDao.findbyblogid(blog.getId(),commentRepository);


        List<User> userList=new ArrayList<User>();
        for(int i=0;i<commentList.size();i++)
        {
            commentList.get(i).setAuthorImageid(userRepository.findById(commentList.get(i).getAuthorid()).get().getImageid());
        }
        model.addAttribute("comments",commentList);

        return "Blog";
    }//查看一篇博客




    @GetMapping(value ="/blog/label/{label}")
    public String getbylabel(@PathVariable String label, Model model) throws ParseException {
        List<Blog> blogList= BlogDao.findbylabel(label,blogRepository);
        model.addAttribute("blogs",blogList);
        return "index";
    }//按标签浏览

    @PostMapping(value = "/blog/search")
    public String search(@RequestParam("name")String name,@RequestParam("button")String label,Model model) throws ParseException {
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
            model.addAttribute("users",userList);
            return "SearchUser";
        }
    }//搜索博客或博主


    @GetMapping(value = "/")
    public String recommon(Model model,HttpSession session) throws ParseException {
        List<Blog> blogList=BlogDao.recommond(blogRepository);

        model.addAttribute("blogs",blogList);
        //session.setAttribute("user",new User());
        return "index";
    }

    @GetMapping(value = "/index.html")
    public String recommon1(Model model) throws ParseException {
        List<Blog> blogList=BlogDao.recommond(blogRepository);
        model.addAttribute("blogs",blogList);
        return "index";
    }//进入主页的推荐页面

    @GetMapping(value = "/blog/new")
    public String new1(Model model,HttpSession session,Map<String,Object> map) throws ParseException {
        if(session.getAttribute("status")==null)
            session.setAttribute("status","false");

        if(session.getAttribute("status").equals("true"))
        {
            User user=(User)session.getAttribute("user");
            List<Blog> blogList=BlogDao.new1(user.getId(),userRepository,blogRepository,followRepository);
            model.addAttribute("blogs",blogList);
            return "index";
        }
        else
        {
            map.put("msg","请先登录");
            return "Login";

        }

    }//动态页面

    @PostMapping(value ="/Blog/edit")
    public String edit(@RequestParam(value = "image") MultipartFile file,@RequestParam("label")String[] label, Blog blog, HttpSession session){

        blog.setDate(Time.getTime());
        Blog blog1=blogRepository.findById(blog.getId()).get();

        blog.setLabel(Arrays.asList(label));

        User user=(User)session.getAttribute("user");
        blog.setAuthorid(user.getId());
        blog.setAuthorname(user.getUsername());
        blog.setNumber(0);
        String fileName = file.getOriginalFilename();
        if(fileName.length()!=0){
            Uploadfile uploadFile = new Uploadfile();
            try {

                uploadFile.setName(fileName);
                uploadFile.setCreatedTime(Time.getTime());
                uploadFile.setContent(new Binary(file.getBytes()));
                uploadFile.setContentType(file.getContentType());
                uploadFile.setSize(file.getSize());
                fileRepository.save(uploadFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
            uploadFile= FileDao.find(uploadFile,fileRepository);
            blog.setFileid(uploadFile.getId());}
        else
        {
            blog.setFileid(blog1.getFileid());
        }
        blogRepository.save(blog);

        return "redirect:/user/info";
    }//修改博客





}
