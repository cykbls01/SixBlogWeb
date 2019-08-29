package com.example.demo.Dao;

import com.example.demo.Entity.Comment;
import com.example.demo.Repository.CommentRepository;

import java.util.ArrayList;
import java.util.List;

public class CommentDao {


    public static List<Comment> findbyblogid(String id, CommentRepository commentRepository)
    {
        List<Comment> commentList=commentRepository.findAll();
        List<Comment> commentList1=new ArrayList<Comment>();
        int size=commentList.size();

        for(int i=0;i<size;i++)
        {
            if(commentList.get(i).getBlogid().equals(id))
            {
                commentList1.add(commentList.get(i));
            }
        }
        return  commentList1;


    }

    public static void deletebyblogid(String id, CommentRepository commentRepository)
    {
        List<Comment> commentList=commentRepository.findAll();

        int size=commentList.size();

        for(int i=0;i<size;i++)
        {
            if(commentList.get(i).getBlogid().equals(id))
            {
                commentRepository.deleteById(commentList.get(i).getId());
            }
        }



    }



}
