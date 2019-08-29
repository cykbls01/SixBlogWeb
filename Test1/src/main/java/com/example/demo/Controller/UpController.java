package com.example.demo.Controller;

import com.example.demo.Dao.BlogDao;
import com.example.demo.Dao.UpDao;
import com.example.demo.Entity.Blog;
import com.example.demo.Entity.Up;
import com.example.demo.Entity.User;
import com.example.demo.Repository.BlogRepository;
import com.example.demo.Repository.UpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Map;

@Controller
public class UpController {
    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private UpRepository upRepository;

    @GetMapping(value = "/blog/like/{id}")
    public String like(@PathVariable String id, HttpSession session, Map<String,Object> map)
    {
        Up up= UpDao.findbyblogid(id,upRepository);
        User user=(User)session.getAttribute("user");
        if(user==null)
        {

            map.put("msg","请先登录");
            return "Login";
        }
        Boolean like=true;
        if(up==null)
        {
            up=new Up();
            up.setBlogid(id);
            up.setUserid(new ArrayList<String>());
            up.getUserid().add(user.getId());
            upRepository.save(up);
            Blog blog=new Blog();
            blog= blogRepository.findById(id).get();
            blog.setNumber(blog.getNumber()+1);
            blogRepository.save(blog);

        }
        else
        {
            if(UpDao.islike(user.getId(),up,upRepository))
            {
                up.getUserid().remove(user.getId());
                upRepository.save(up);
                Blog blog=new Blog();
                blog= blogRepository.findById(id).get();
                blog.setNumber(blog.getNumber()-1);
                blogRepository.save(blog);



            }
            else
            {
                up.getUserid().add(user.getId());
                upRepository.save(up);
                Blog blog=new Blog();
                blog= blogRepository.findById(id).get();
                blog.setNumber(blog.getNumber()+1);
                blogRepository.save(blog);


            }



        }


        return "redirect:/blog/get/"+id;
    }//点赞or取消点赞


}
