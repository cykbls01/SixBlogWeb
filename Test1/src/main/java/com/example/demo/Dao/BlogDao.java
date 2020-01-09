package com.example.demo.Dao;

import com.example.demo.Entity.Blog;
import com.example.demo.Entity.User;
import com.example.demo.Repository.BlogRepository;
import com.example.demo.Repository.FollowRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Tools.Convert;
import com.example.demo.Tools.Time;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


public class BlogDao {

    public static List<Blog> search(String name,BlogRepository blogRepository) throws ParseException {
        List<Blog> blogList = blogRepository.findAll();
        List<Blog> blogList1=new ArrayList<Blog>();
        int pan[]=new int[10000];
        for(int i=0;i<10000;i++)
        {
            pan[i]=0;
        }
        int size;
        for (int i = 0; i < blogList.size(); i++)
        {
            if(blogList.get(i).getTheme().contains(name)==true||blogList.get(i).getContent().contains(name)==true)
            {
                size=blogList.get(i).getContent().length();
                String str=Convert.splitAndFilterString(blogList.get(i).getContent(),blogList.get(i).getContent().length());
                size=str.length();
                if(size>50) size=50;
                blogList.get(i).setContent(str.substring(0,size));
                blogList1.add(blogList.get(i));


            }

        }
        blogList1=sort(blogList1);
        return blogList1;
    }

    public static List<Blog> findbyAuthorid(String id,BlogRepository blogRepository) throws ParseException {
        List<Blog> blogList = blogRepository.findAll();
        List<Blog> blogList1=new ArrayList<Blog>();

        //int size;
        for(int i=0;i<blogList.size();i++)
        {
            if(blogList.get(i).getAuthorid().equals(id)==true)
            {
                //size=blogList.get(i).getContent().length();
                //String str=Convert.splitAndFilterString(blogList.get(i).getContent(),blogList.get(i).getContent().length());
                //size=str.length();
                //if(size>50) size=50;
                //blogList.get(i).setContent(str.substring(0,size));
                blogList1.add(blogList.get(i));
            }
        }
        blogList1=sort(blogList1);
        return blogList1;


    }

    public static List<Blog> sort(List<Blog> blogList) throws ParseException {

        int size=blogList.size();
        for(int i=0;i<size-1;i++)
        {
            for(int j=0;j<size-1;j++)
            {

                    if (Time.compare(blogList.get(j).getDate(),blogList.get(j+1).getDate())==false)
                    {
                        //Blog blog=blogList.get(i);
                        //blogList.set(i,blogList.get(i+1));
                        //blogList.set(i+1,blog);
                        Collections.swap(blogList,j,j+1);

                    }



            }


        }
        return blogList;

    }

    public static List<Blog> sortbynumber(List<Blog> blogList) throws ParseException {

        int size=blogList.size();
        for(int i=0;i<size-1;i++)
        {
            for(int j=1;j<size;j++)
            {
                if(i==j) continue;
                else
                {
                    if (blogList.get(i).getNumber()<blogList.get(i+1).getNumber())
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
        List<Blog> blogList = blogRepository.findAll();
        List<Blog> blogList1=new ArrayList<Blog>();
        int size;
        for (int i = 0; i < blogList.size(); i++)
        {
            if(blogList.get(i).getLabel()!=null&&blogList.get(i).getLabel().contains(label)==true)
            {
                size=blogList.get(i).getContent().length();
                String str=Convert.splitAndFilterString(blogList.get(i).getContent(),blogList.get(i).getContent().length());
                size=str.length();
                if(size>50) size=50;
                blogList.get(i).setContent(str.substring(0,size));
                blogList1.add(blogList.get(i));
            }
        }
        blogList1=sortbynumber(blogList1);
        return blogList1;
    }

    public static List<Blog> recommond(BlogRepository blogRepository) throws ParseException {
        List<Blog> blogList=blogRepository.findAll();
        List<Blog> blogList1=new ArrayList<Blog>();
        //int size=blogList.size();
        //if(size>10) size=10;
        //blogList=sortbynumber(blogList).subList(0,size);
        for(int i=0;i<blogList.size();i++)
        {
            //size=blogList.get(i).getContent().length();
            //String str=Convert.splitAndFilterString(blogList.get(i).getContent(),blogList.get(i).getContent().length());
            //size=str.length();
            //if(size>50) size=50;
            //blogList.get(i).setContent(str.substring(0,size));
        }
        return blogList;
    }

    public static List<Blog> new1(String id, UserRepository userRepository, BlogRepository blogRepository, FollowRepository followRepository) throws ParseException {
        List<Blog> blogList=new ArrayList<Blog>();
        List<User> userList=FollowDao.findidlist(id,followRepository,userRepository);
        for(int i=0; i<userList.size();i++)
            blogList.addAll(findbyAuthorid(userList.get(i).getId(),blogRepository));
        blogList=sort(blogList);
        int size=blogList.size();
        if(size>10) size=10;
        blogList.subList(0,size);
        return blogList;
    }

}
