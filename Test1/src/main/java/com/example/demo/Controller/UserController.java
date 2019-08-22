package com.example.demo.Controller;


import com.example.demo.Dao.UserDao;
import com.example.demo.Entity.User;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Tools.Convert;
import com.example.demo.Tools.Mail;
import com.example.demo.Tools.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class UserController {
    @Autowired
    private UserRepository userRepository;


    @Autowired
    private MailService mail;



    @PostMapping(value ="/login")//三种情况，邮箱不存在，密码错误，全部匹配
    public String login(@RequestParam("email")String email,
                         @RequestParam("password")String password,
                         Map<String,Object> map,
                         HttpSession session) throws NoSuchAlgorithmException {
        password= Convert.SHA(password);//密码转换
        if(UserDao.findbyemail(email,userRepository)==null)
        {
            map.put("msg","邮箱不存在");
            return "login";
        }
        else if(UserDao.findbyemail(email,userRepository).getPassword().equals(password)==true)
        {
            map.put("msg","登录成功");
            return "login";
        }
        else
        {
            map.put("msg","密码错误");
            return "login";
        }
    }

    @PostMapping(value ="/register")//四种情况，邮箱存在，用户名存在，密码不符合要求，全部符合
    public String register(User user,
                        Map<String,Object> map,
                        HttpSession session) throws NoSuchAlgorithmException {

        if(UserDao.findbyemail(user.getEmail(),userRepository)!=null)
        {
            map.put("msg","邮箱已存在");
            return "register";
        }
        else if(UserDao.findbyusername(user.getUsername(),userRepository)!=null)
        {
            map.put("msg","用户名重复");
            return "register";
        }
        else
        {
            user.setPassword(Convert.SHA(user.getPassword()));
            userRepository.save(user);
            return "register";
        }
    }

    @PostMapping(value ="/findpwd")//两种情况，邮箱不存在，邮箱存在
    public String findpwd(@RequestParam("email")String email,
                        Map<String,Object> map,
                        HttpSession session) throws NoSuchAlgorithmException {

        if(UserDao.findbyemail(email,userRepository)==null)
        {
            map.put("msg","邮箱不存在");
            return "findpwd";
        }
        else
        {
            User user=UserDao.findbyemail(email,userRepository);

            user.setPassword(Convert.SHA(String.valueOf((int)(1+Math.random()*(100000)))));
            userRepository.save(user);
            map.put("msg","邮件已发送到您的邮箱");
            return "findpwd";
        }

    }
    @ResponseBody
    @RequestMapping("/hello")
    public String helloWorld(){

        mail.sendSimpleMail("2541601705@qq.com","测试一下","测试邮件正文");
        return "success";
    }










}
