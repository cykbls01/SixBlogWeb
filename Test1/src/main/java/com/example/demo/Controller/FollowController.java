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
    public String get(HttpSession session, Model model)
    {
        User user=(User)session.getAttribute("user");
        List<User> userList= FollowDao.findfollowbyid(user.getId(),followRepository,userRepository);
        session.setAttribute("followlists",userList);
        session.setAttribute("follownumber",userList.size());
        return "user/follow";
    }
    @PostMapping(value = "/follow/add")
    public String add(@RequestParam("id")String id, HttpSession session)
    {
        User user=(User)session.getAttribute("user");
        Follow follow=FollowDao.findbyid(user.getId(),followRepository);
        if(follow!=null)
        {
            follow.getIdlist().add(id);
            follow.setNum(follow.getNum()+1);
            followRepository.save(follow);

        }
        else
        {
            follow=new Follow();
            follow.setIdlist(new ArrayList<String>());
            follow.getIdlist().add(id);
            follow.setUserid(user.getId());
            follow.setNum(1);
            followRepository.save(follow);
        }
        user=userRepository.findById(id).get();
        user.setFollow(user.getFollow()+1);
        userRepository.save(user);

        return "Bingo";
    }

    @GetMapping(value = "/follow/delete/{id}")
    public String delete(@PathVariable String id, HttpSession session)
    {
        User user=(User)session.getAttribute("user");
        Follow follow=FollowDao.findbyid(user.getId(),followRepository);
        if(follow!=null)
        {
            //follow.getIdlist().add(id);
            follow.getIdlist().remove(id);
            follow.setNum(follow.getNum()-1);
            followRepository.save(follow);

        }
        else
        {

        }
        user=userRepository.findById(id).get();
        user.setFollow(user.getFollow()-1);

        return "user/follow";
    }







}
