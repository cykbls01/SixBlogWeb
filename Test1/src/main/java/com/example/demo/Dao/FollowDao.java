package com.example.demo.Dao;

import com.example.demo.Entity.Follow;
import com.example.demo.Entity.User;
import com.example.demo.Repository.FollowRepository;
import com.example.demo.Repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

public class FollowDao {
    public static List<User> findfollowbyid(String id, FollowRepository followRepository, UserRepository userRepository)
    {
        List<User> userList=new ArrayList<User>();
        List<String> idList=new ArrayList<String>();
        List<Follow> followList=followRepository.findAll();
        for(int i=0;i<followList.size();i++)
        {
            if(followList.get(i).getUserid().equals(id)==true)
            {
                idList=followList.get(i).getIdlist();
                break;
            }


        }

        for(int i=0;i<idList.size();i++)
        {
            userList.add(userRepository.findById(idList.get(i)).get());


        }
        return userList;
    }







}
