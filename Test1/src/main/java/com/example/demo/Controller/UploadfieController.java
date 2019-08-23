package com.example.demo.Controller;

import com.example.demo.Entity.Uploadfile;
import com.example.demo.Repository.FileRepository;
import com.example.demo.Tools.Time;

import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;



@Controller
public class UploadfieController{
    @Autowired
    private FileRepository fileRepository;

    @PostMapping("/file/uploadImage")
    public String uploadImage(@RequestParam(value = "image") MultipartFile file){

        String fileName = file.getOriginalFilename();
        try {
            Uploadfile uploadFile = new Uploadfile();
            uploadFile.setName(fileName);
            uploadFile.setCreatedTime(Time.getTime());
            uploadFile.setContent(new Binary(file.getBytes()));
            uploadFile.setContentType(file.getContentType());
            uploadFile.setSize(file.getSize());
            fileRepository.save(uploadFile);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Bingo";

    }

    @GetMapping(value = "/file/image/{id}", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    @ResponseBody
    public byte[] image(@PathVariable String id){
        byte[] data = null;
        Uploadfile file = fileRepository.findById(id).get();
        if(file != null){
            data = file.getContent().getData();
        }
        return data;
    }




}
