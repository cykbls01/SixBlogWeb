package com.example.demo.Dao;

import com.example.demo.Entity.User;
import com.example.demo.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class UserDao {



    public static User findbyemail(String email,UserRepository userRepository)
    {
        List<User> userList=userRepository.findAll();
        for(int i=0;i<userList.size();i++)
        {
            if(userList.get(i).getEmail().equals(email))
                return userList.get(i);
        }
        return null;
    }
    public static User findbyusername(String username,UserRepository userRepository)
    {
        List<User> userList=userRepository.findAll();
        for(int i=0;i<userList.size();i++)
        {
            if(userList.get(i).getUsername().equals(username))
                return userList.get(i);
        }
        return null;
    }
    public static List<User> searchbyusername(String username,UserRepository userRepository)
    {
        List<User> userList=userRepository.findAll();
        List<User> userList1=new ArrayList<User>();
        int size=userList.size();
        for(int i=0;i<size;i++)
        {
            if(userList.get(i).getUsername().contains(username))
            {
                userList1.add(userList.get(i));
            }

        }
        return userList1;
    }





}
