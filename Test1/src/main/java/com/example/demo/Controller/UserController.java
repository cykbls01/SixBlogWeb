package com.example.demo.Controller;


import com.example.demo.Dao.UserDao;
import com.example.demo.Entity.User;
import com.example.demo.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @PostMapping(value ="/login")
    public String Login(@RequestParam("username")String email,
                       @RequestParam("password")String password,
                       Map<String,Object> map,
                       HttpSession session){
        User user=UserDao.findbyemail(email,userRepository);
        if(user==null)
        {
            map.put("msg","用户邮箱不存在");
            return "Login";
        }
        else if(user.getPassword().equals(password)==false)
        {
            map.put("msg","密码错误");
            return "Login";

        }
        else
        {
            map.put("msg","登陆成功");
            return "Login";
            //session.setAttribute("user",username);
            //return "redirect:/main.html";
        }
    }
    @PostMapping(value ="/register")
    public String Register(User user,
                        Map<String,Object> map,
                        HttpSession session){
        if(UserDao.findbyusername(user.getUsername(),userRepository)!=null)
        {
            map.put("msg","用户名重复");
            return "Login";
        }
        else if(UserDao.findbyemail(user.getEmail(),userRepository)!=null)
        {
            map.put("msg","邮箱重复");
            return "Login";
        }
        else if(true)
        {
            map.put("msg","密码不符合规则");
            return "Login";
        }
        else
        {
            userRepository.save(user);
            return "Login";
        }

    }




    @ResponseBody
    @RequestMapping("/save")
    public String Save(){

        User user=new User();
        user.setId("17373273");
        user.setUsername("cyyk");
        user.setPassword("123456");
        userRepository.save(user);
        return "success";
    }


    @ResponseBody
    @RequestMapping("/save1")
    public String Save1(){

        User user=new User();
        //user.setId("17373273");
        user.setUsername("cyyk");
        user.setPassword("123456");
        userRepository.save(user);
        return "success";
    }





}
