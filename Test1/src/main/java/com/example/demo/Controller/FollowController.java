package com.example.demo.Controller;

import com.example.demo.Dao.FollowDao;
import com.example.demo.Entity.User;
import com.example.demo.Repository.FollowRepository;
import com.example.demo.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class FollowController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FollowRepository followRepository;

    @GetMapping(value = "/follow/{id}")
    public String get(@PathVariable String id, HttpSession session)
    {
        List<User> userList= FollowDao.findfollowbyid(id,followRepository,userRepository);
        session.setAttribute("followlist",userList);
        session.setAttribute("follownumber",userList.size());

        return "user/follow";
    }
    @GetMapping(value = "/follow/add/{id}")
    public String add(@PathVariable String id, HttpSession session)
    {
        User user=(User)session.getAttribute("user");


        return "user/follow";
    }







}
