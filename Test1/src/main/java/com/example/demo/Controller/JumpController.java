package com.example.demo.Controller;


import com.example.demo.Dao.BlogDao;
import com.example.demo.Dao.FollowDao;
import com.example.demo.Entity.Blog;
import com.example.demo.Entity.Image;
import com.example.demo.Entity.User;
import com.example.demo.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class JumpController {
    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FollowRepository followRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ImageRepository imageRepository;


    @RequestMapping(value = "/user/info")
    public String indextozone(HttpSession session, Model model) throws ParseException {
        User user = (User) session.getAttribute("user");
        List<Blog> blogList = BlogDao.findbyAuthorid(user.getId(), blogRepository);
        session.setAttribute("blognumber", blogList.size());
        model.addAttribute("blogs", blogList);
        model.addAttribute("blognumber", blogList.size());
        try {
            model.addAttribute("lastupdate", blogList.get(0).getDate());
        } catch (Exception e) {
            model.addAttribute("lastupdate", "");
        }
        return "Zone";
    }//跳转到个人空间

    @RequestMapping(value = "/Addblog.html")
    public String addblog(HttpSession session, Map<String, Object> map) {

        if (session.getAttribute("status") == null)
            session.setAttribute("status", "false");

        if (session.getAttribute("status").equals("true")) {
            return "redirect:/Addblog";


        } else {
            map.put("msg", "请先登录");
            return "Login";

        }

    }

    @GetMapping(value = "/Editblog.html/{id}")
    public String editblog(@PathVariable String id, HttpSession session, Map<String, Object> map, Model model) {


        Blog blog = blogRepository.findById(id).get();

        User user=(User)session.getAttribute("user");
        if(user==null||user.getId().equals(blog.getAuthorid())==false)
        {
            return "redirect:/index.html";
        }
        else{
        model.addAttribute("blog", blog);
        return "Editblog";}


    }

    @GetMapping(value = "/zone/author/{id}")
    public String zone(@PathVariable String id, Model model, HttpSession session) throws ParseException {
        User user = (User) session.getAttribute("user");

        if (user != null && user.getId().equals(id)) return "redirect:/user/info";
        User author = userRepository.findById(id).get();
        model.addAttribute("author", author);
        List<Blog> blogList = BlogDao.findbyAuthorid(author.getId(), blogRepository);


        model.addAttribute("blogs", blogList);
        model.addAttribute("blognumber", blogList.size());
        if (user != null)
            model.addAttribute("fstatus", FollowDao.isfollow(user.getId(), author.getId(), followRepository, userRepository));
        else model.addAttribute("fstatus", false);
        return "Zone_author";


    }//跳转到作者的个人空间

    @RequestMapping(value = "/user/image")
    public String getAllimage(HttpSession session,Model model)
    {
        User user=(User)session.getAttribute("user");
        List<Image> imageList=imageRepository.findAll();
        List<Image> imageList1=new ArrayList<Image>();
        for(int i=0;i<imageList.size();i++)
        {
            if(imageList.get(i).getUserid().equals(user.getId()))
            {
                imageList1.add(imageList.get(i));
            }
        }
        model.addAttribute("images",imageList1);
        return "saveCommentPicture";



    }







}
