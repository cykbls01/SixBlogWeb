package com.example.demo.Dao;

import com.example.demo.Entity.Uploadfile;
import com.example.demo.Repository.FileRepository;

import java.util.List;

public class FileDao {

    public static Uploadfile find(Uploadfile file, FileRepository fileRepository)
    {
        List<Uploadfile> uploadfiles=fileRepository.findAll();
        for(int i=0;i<uploadfiles.size();i++)
        {
            if(uploadfiles.get(i).getName().equals(file.getName())&&uploadfiles.get(i).getCreatedTime().equals(file.getCreatedTime()))
            file=uploadfiles.get(i);

        }
        return file;


    }



}
