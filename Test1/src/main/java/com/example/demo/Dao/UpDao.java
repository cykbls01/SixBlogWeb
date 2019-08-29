package com.example.demo.Dao;

import com.example.demo.Entity.Up;
import com.example.demo.Repository.UpRepository;

import java.util.List;

public class UpDao {
    public static Up findbyblogid(String id, UpRepository upRepository)
    {
        List<Up> upList=upRepository.findAll();
        for(int i=0;i<upList.size();i++)
        {
            if(upList.get(i).getBlogid().equals(id))
                return upList.get(i);

        }
        return null;
    }

    public static boolean islike(String uid,Up up,UpRepository upRepository)
    {
        if(up.getUserid().contains(uid))
            return true;
        else return false;


    }




}
