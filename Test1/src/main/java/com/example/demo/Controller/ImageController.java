package com.example.demo.Controller;

import com.example.demo.Dao.FileDao;
import com.example.demo.Entity.Image;
import com.example.demo.Entity.Uploadfile;
import com.example.demo.Entity.User;
import com.example.demo.Repository.FileRepository;
import com.example.demo.Repository.ImageRepository;
import com.example.demo.Tools.Time;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
@Controller
public class ImageController {
    @Autowired
    private ImageRepository imageRepository;

    @PostMapping("/Image/add")
    public String add(@RequestParam(value = "image") MultipartFile file, HttpSession session){

        String fileName = file.getOriginalFilename();
        User user=(User)session.getAttribute("user");
        try {
            Image uploadFile = new Image();
            uploadFile.setName(fileName);
            uploadFile.setCreatedTime(Time.getTime());
            uploadFile.setContent(new Binary(file.getBytes()));
            uploadFile.setContentType(file.getContentType());
            uploadFile.setSize(file.getSize());
            uploadFile.setUserid(user.getId());
            imageRepository.save(uploadFile);


        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:/user/image";

    }//上传图片

    @GetMapping("/Image/delete/{id}")
    public String delete(@PathVariable String id, HttpSession session){

        imageRepository.deleteById(id);
        return "redirect:/user/image";

    }//上传图片

    @GetMapping(value = "/Image/get/{id}", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    @ResponseBody
    public byte[] image(@PathVariable String id){
        byte[] data = null;
        Image image;
        try{
            image = imageRepository.findById(id).get();}
        catch(Exception e)
        {
            image=null;
        }
        if(image!= null){
            data = image.getContent().getData();
        }
        return data;
    }//获取头像


}
