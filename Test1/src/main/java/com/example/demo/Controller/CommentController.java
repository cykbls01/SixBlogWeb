package com.example.demo.Controller;

import com.example.demo.Entity.Blog;
import com.example.demo.Entity.CComment;
import com.example.demo.Entity.Comment;
import com.example.demo.Entity.User;
import com.example.demo.Repository.CommentRepository;
import com.example.demo.Tools.Time;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;


@Controller
public class CommentController {
    @Autowired
    private CommentRepository commentRepository;

    @PostMapping(value ="/comment/add")
    public String add(Comment comment, HttpSession session)
    {
        comment.setDate(Time.getTime());
        User user=(User)session.getAttribute("user");
        comment.setAuthorid(user.getId());
        comment.setAuthorname(user.getUsername());
        commentRepository.save(comment);
        return "redirect:/blog/get/"+comment.getBlogid();
    }
    @GetMapping(value ="/comment/delete/{id}")
    public String delete(@RequestParam("comment")String id)
    {

        commentRepository.deleteById(id);
        return "comment/delete";
    }
    @PostMapping(value ="/ccomment/add")
    public String add(CComment ccomment,@RequestParam("comment")String id)
    {
        ccomment.setDate(Time.getTime());
        Comment comment=commentRepository.findById(id).get();

        commentRepository.save(comment);
        return "comment/add";
    }
    @PostMapping(value ="/ccomment/delete")
    public String delete(CComment ccomment,@RequestParam("comment")String id)
    {
        ccomment.setDate(Time.getTime());
        Comment comment=commentRepository.findById(id).get();
        /*for(int i=0;i<comment.getCommentList().size();i++)
        {
            if(comment.getCommentList().get(i).getId().equals(ccomment.getId()))
            {
                comment.getCommentList().remove(i);
                break;
            }
        }*/
        commentRepository.save(comment);
        return "comment/delete";
    }









}
