package com.example.demo.Dao;

import com.example.demo.Entity.Follow;
import com.example.demo.Entity.User;
import com.example.demo.Repository.FollowRepository;
import com.example.demo.Repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

public class FollowDao {
    public static List<User> findidlist(String id, FollowRepository followRepository, UserRepository userRepository)
    {
        List<User> userList=new ArrayList<User>();
        List<String> idList=new ArrayList<String>();
        List<Follow> followList=followRepository.findAll();
        idList=findbyid(id,followRepository).getIdlist();
        if(idList==null) return userList;

        for(int i=0;i<idList.size();i++)
        {
            userList.add(userRepository.findById(idList.get(i)).get());


        }
        return userList;
    }

    public static List<User> findfidlist(String id, FollowRepository followRepository, UserRepository userRepository)
    {
        List<User> userList=new ArrayList<User>();
        List<String> fidList=new ArrayList<String>();
        List<Follow> followList=followRepository.findAll();
        fidList=findbyid(id,followRepository).getFidlist();
        if(fidList==null) return userList;
        for(int i=0;i<fidList.size();i++)
        {
            userList.add(userRepository.findById(fidList.get(i)).get());


        }
        return userList;
    }


    public static boolean isfollow(String id, String fid,FollowRepository followRepository, UserRepository userRepository)
    {

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

        if(idList.contains(fid))
        return true;
        else
            return false;
    }

    public static Follow findbyid(String id,FollowRepository followRepository)
    {

        List<Follow> followList=followRepository.findAll();
        for(int i=0;i<followList.size();i++)
        {
            if(followList.get(i).getUserid().equals(id)==true)
            {
                return followList.get(i);
            }


        }
        return new Follow();


    }

    public static void add(String uid,String id,FollowRepository followRepository)
    {
        Follow follow=findbyid(uid,followRepository);
        if(follow!=null)
        {
            if(follow.getIdlist()==null)
                follow.setIdlist(new ArrayList<String>());
            follow.getIdlist().add(id);
            follow.setNum(follow.getNum()+1);
            followRepository.save(follow);

        }
        else
        {
            follow=new Follow();
            follow.setIdlist(new ArrayList<String>());
            follow.getIdlist().add(id);
            follow.setUserid(uid);
            follow.setNum(1);
            followRepository.save(follow);
        }
        follow=findbyid(id,followRepository);
        if(follow!=null)
        {
            if(follow.getFidlist()==null)
                follow.setFidlist(new ArrayList<String>());
            follow.getFidlist().add(uid);
            follow.setFnum(follow.getFnum()+1);
            followRepository.save(follow);

        }
        else
        {
            follow=new Follow();
            follow.setFidlist(new ArrayList<String>());
            follow.getFidlist().add(uid);
            follow.setUserid(id);
            follow.setFnum(1);
            followRepository.save(follow);
        }



    }

    public static void delete(String uid,String id,FollowRepository followRepository)
    {
        Follow follow=findbyid(uid,followRepository);
        follow.getIdlist().remove(id);
        follow.setNum(follow.getNum()-1);
        followRepository.save(follow);
        follow=findbyid(id,followRepository);
        follow.getFidlist().remove(uid);
        follow.setFnum(follow.getFnum()-1);
        followRepository.save(follow);

    }








}
