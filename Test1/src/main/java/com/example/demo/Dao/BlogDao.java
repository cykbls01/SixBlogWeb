package com.example.demo.Dao;

import com.example.demo.Entity.Blog;
import com.example.demo.Repository.BlogRepository;
import com.example.demo.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;


public class BlogDao {

    public static List<Blog> search(String name,BlogRepository blogRepository) {
        List<Blog> blogList = blogRepository.findAll();
        List<Blog> blogList1=new ArrayList<Blog>();
        int pan[]=new int[10000];
        for(int i=0;i<10000;i++)
        {
            pan[i]=0;
        }
        for (int i = 0; i < blogList.size(); i++)
        {
            if(blogList.get(i).getTheme().contains(name)==false&&blogList.get(i).getContent().contains(name)==false)
            {
                pan[i]=1;
                blogList1.add(blogList.get(i));

            }

        }
        for(int k=0; k< blogList.size();k++)
        {
            if(pan[k]==1) continue;
            for(int i=2;i<=5&&i<name.length();i++)
            {
                if(pan[k]==1) break;
                for(int j=0;j<name.length();j++)
                {
                String str=name.substring(j,j+i);
                if(blogList.get(k).getTheme().contains(str)||blogList.get(i).getContent().contains(str))
                {
                    pan[k]=1;
                    blogList1.add(blogList.get(k));
                    break;
                }

                }
            }
        }


        return blogList1;
    }




}
