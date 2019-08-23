package com.example.demo.Dao;

import com.example.demo.Entity.Blog;
import com.example.demo.Repository.BlogRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Tools.Time;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.text.ParseException;
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
            if(blogList.get(i).getTheme().contains(name)==true||blogList.get(i).getContent().contains(name)==true)
            {
                pan[i]=1;
                blogList1.add(blogList.get(i));

            }

        }
        /*for(int k=0; k< blogList.size();k++)
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
        }*/


        return blogList1;
    }

    public static List<Blog> findbyAuthorid(String id,BlogRepository blogRepository) throws ParseException {
        List<Blog> blogList = blogRepository.findAll();
        int size=blogList.size();
        for(int i=0;i<size;i++)
        {
            if(blogList.get(i).getAuthorid().equals(id)==false)
            {
                blogList.remove(i);
            }
        }
        blogList=sort(blogList);
        return blogList;


    }

    public static List<Blog> sort(List<Blog> blogList) throws ParseException {

        int size=blogList.size();
        for(int i=0;i<size;i++)
        {
            for(int j=1;j<size;j++)
            {
                if(i==j) continue;
                else
                {
                    if (Time.compare(blogList.get(i).getDate(),blogList.get(j).getDate())==false)
                    {
                        Blog blog1=blogList.get(i);
                        Blog blog2=blogList.get(j);
                        blogList.remove(i);
                        blogList.add(i,blog2);
                        blogList.remove(j);
                        blogList.add(j,blog1);
                    }


                }
            }


        }
        return blogList;

    }
    public static List<Blog> findbylabel(String label,BlogRepository blogRepository) throws ParseException {
        List<Blog> blogList=blogRepository.findAll();
        int size=blogList.size();
        for(int i=0;i<size;i++)
        {
            if(blogList.get(i).getLabel().contains(label)==false)
                blogList.remove(i);
        }
        blogList=sort(blogList);
        return blogList;


    }





}
