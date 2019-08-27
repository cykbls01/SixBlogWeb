package com.example.demo.Controller;

import com.example.demo.Dao.FollowDao;
import com.example.demo.Entity.Follow;
import com.example.demo.Entity.User;
import com.example.demo.Repository.FollowRepository;
import com.example.demo.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class FollowController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FollowRepository followRepository;

    @GetMapping(value = "/follow/get")
    public String followget(HttpSession session, Model model)
    {
        User user=(User)session.getAttribute("user");
        List<User> userList= FollowDao.findidlist(user.getId(),followRepository,userRepository);
        session.setAttribute("followlists",userList);
        model.addAttribute("follows",userList);
        System.out.println(userList.size());
        return "Zone_follow";
    }

    @GetMapping(value = "/fan/get")
    public String fanget(HttpSession session, Model model)
    {
        User user=(User)session.getAttribute("user");
        List<User> userList= FollowDao.findfidlist(user.getId(),followRepository,userRepository);
        session.setAttribute("followlists",userList);
        model.addAttribute("fans",userList);
        System.out.println(userList.size());
        return "Zone_fan";
    }
    @GetMapping(value = "/follow/add/{id}")
    public String add(@PathVariable String id, HttpSession session)
    {
        User user=(User)session.getAttribute("user");
        FollowDao.add(user.getId(),id,followRepository);
        user.setFollow(user.getFollow()+1);
        userRepository.save(user);
        session.setAttribute("user",user);
        user=userRepository.findById(id).get();
        user.setFan(user.getFan()+1);
        userRepository.save(user);
        return "Bingo";
    }
    @PostMapping(value = "/follow/add")
    public String add1(@RequestParam("id")String id, HttpSession session)
    {
        User user=(User)session.getAttribute("user");
        FollowDao.add(user.getId(),id,followRepository);
        user.setFollow(user.getFollow()+1);
        userRepository.save(user);
        session.setAttribute("user",user);
        user=userRepository.findById(id).get();
        user.setFan(user.getFan()+1);
        userRepository.save(user);
        return "Bingo";
    }
    @GetMapping(value = "/follow/delete/{id}")
    public String delete(@PathVariable String id, HttpSession session)
    {
        User user=(User)session.getAttribute("user");
        FollowDao.delete(user.getId(),id,followRepository);
        user.setFollow(user.getFollow()-1);
        userRepository.save(user);
        session.setAttribute("user",user);
        user=userRepository.findById(id).get();
        user.setFan(user.getFan()-1);
        userRepository.save(user);
        return "Zone_follow";
    }










}
