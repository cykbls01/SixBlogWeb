package com.example.demo.Controller;

import com.example.demo.Entity.CComment;
import com.example.demo.Entity.Comment;
import com.example.demo.Repository.BlogRepository;
import com.example.demo.Repository.CommentRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Tools.Time;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class CommentController {
    @Autowired
    private CommentRepository commentRepository;

    @PostMapping(value ="/comment/add")
    public String add(Comment comment)
    {
        comment.setDate(Time.getTime());
        commentRepository.save(comment);
        return "comment/add";
    }
    @PostMapping(value ="/comment/delete")
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
        comment.getCommentList().add(ccomment);
        commentRepository.save(comment);
        return "comment/add";
    }
    @PostMapping(value ="/ccomment/delete")
    public String delete(CComment ccomment,@RequestParam("comment")String id)
    {
        ccomment.setDate(Time.getTime());
        Comment comment=commentRepository.findById(id).get();
        for(int i=0;i<comment.getCommentList().size();i++)
        {
            if(comment.getCommentList().get(i).getId().equals(ccomment.getId()))
            {
                comment.getCommentList().remove(i);
                break;
            }
        }
        commentRepository.save(comment);
        return "comment/delete";
    }






}
