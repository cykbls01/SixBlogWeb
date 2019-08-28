package com.example.demo.Controller;


import com.example.demo.Dao.BlogDao;
import com.example.demo.Dao.FollowDao;
import com.example.demo.Dao.UserDao;
import com.example.demo.Entity.Blog;
import com.example.demo.Entity.User;
import com.example.demo.Repository.BlogRepository;
import com.example.demo.Repository.FollowRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Tools.Convert;
import com.example.demo.Tools.MailService;

import com.example.demo.Tools.Time;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

@Controller
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FollowRepository followRepository;

    @Autowired
    private MailService mail;

    @Autowired
    private BlogRepository blogRepository;


    @PostMapping(value ="/user/login")//三种情况，邮箱不存在，密码错误，全部匹配
    public String login(@RequestParam("email")String email,
                        @RequestParam("password")String password,
                        Map<String,Object> map,
                        HttpSession session, Model model) throws NoSuchAlgorithmException, ParseException {
        password= Convert.SHA(password);//密码转换
        if(UserDao.findbyemail(email,userRepository)==null)
        {
            map.put("msg","邮箱不存在");
            return "Login";
        }
        else if(UserDao.findbyemail(email,userRepository).getPassword().equals(password)==true)
        {
            User user=UserDao.findbyemail(email,userRepository);
            session.setAttribute("user",user);
            session.setAttribute("status","true");
            return "redirect:/";

        }
        else
        {
            map.put("msg","密码错误");
            return "Login";
        }
    }

    @PostMapping(value ="/user/register")//四种情况，邮箱存在，用户名存在，密码不符合要求，全部符合
    public String register(User user,
                        Map<String,Object> map,
                        HttpSession session) throws NoSuchAlgorithmException {

        if(UserDao.findbyemail(user.getEmail(),userRepository)!=null)
        {
            map.put("msg","邮箱已存在");
            return "Register";
        }
        else if(UserDao.findbyusername(user.getUsername(),userRepository)!=null)
        {
            map.put("msg","用户名重复");
            return "Register";
        }
        else
        {
            user.setPassword(Convert.SHA(user.getPassword()));
            user.setContent(" ");
            user.setBirth(Time.getTime().substring(0,10));
            user.setFollow(0);
            userRepository.save(user);
            user=UserDao.findbyusername(user.getUsername(),userRepository);
            session.setAttribute("user",user);
            session.setAttribute("status","true");
            return "redirect:/";
        }
    }




    @PostMapping(value ="/user/findpwd")//两种情况，邮箱不存在，邮箱存在
    public String findpwd(@RequestParam("email")String email,
                        Map<String,Object> map,
                        HttpSession session) throws NoSuchAlgorithmException {

        if(UserDao.findbyemail(email,userRepository)==null)
        {
            map.put("msg","邮箱不存在");
            return "Findpwd";
        }
        else
        {
            User user=UserDao.findbyemail(email,userRepository);
            session.setAttribute("email",user.getEmail());
            String yanzhengma=(String.valueOf((int)(1+Math.random()*(100000))));
            mail.sendSimpleMail(user.getEmail(),"密码找回邮件",yanzhengma);
            session.setAttribute("pan",yanzhengma);
            return "redirect:/Modifypwd.html";
        }

    }

    @PostMapping(value ="/user/modifypwd")
    public String modifypwd(@RequestParam("yanzhengma")String yanzhengma,
                         @RequestParam("password")String password,
                         Map<String,Object> map,
                         HttpSession session) throws NoSuchAlgorithmException {
        if(yanzhengma.equals(session.getAttribute("pan"))==true)
        {
            String email=(String)session.getAttribute("email");
            User user=UserDao.findbyemail(email,userRepository);
            user.setPassword(Convert.SHA(password));
            userRepository.save(user);
            return "redirect:/Login.html";
        }
        else
        {
            map.put("msg","验证码错误");
            return "Modifypwd";
        }
    }

    @PostMapping(value ="/user/modify")
    public String modify(User user,
                         Map<String,Object> map,
                         HttpSession session) throws NoSuchAlgorithmException {
        if(((User)session.getAttribute("user")).getEmail().equals(user.getEmail())==false&&UserDao.findbyemail(user.getEmail(),userRepository)!=null)
        {
            map.put("msg","邮箱已存在");
            return "Modifyinfo";
        }
        else if(((User)session.getAttribute("user")).getUsername().equals(user.getUsername())==false&&UserDao.findbyusername(user.getUsername(),userRepository)!=null)
        {
            map.put("msg","用户名重复");
            return "Modifyinfo";
        }
        else
        {
            if(user.getPassword().equals(((User) session.getAttribute("user")).getPassword())==false)
            user.setPassword(Convert.SHA(user.getPassword()));
            session.setAttribute("user",user);
            userRepository.save(user);
            return "redirect:/user/info";
        }
    }

    @GetMapping(value = "/user/logout")
    public String logout(HttpSession session)
    {
        session.removeAttribute("user");
        session.setAttribute("status",false);
        return "redirect:/";


    }


















}
