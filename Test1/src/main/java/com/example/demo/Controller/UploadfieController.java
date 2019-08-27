package com.example.demo.Controller;

import com.example.demo.Dao.FileDao;
import com.example.demo.Entity.Uploadfile;
import com.example.demo.Entity.User;
import com.example.demo.Repository.FileRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Tools.Time;

import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


@Controller
public class UploadfieController{
    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private UserRepository userRepository;


    @PostMapping("/file/uploadImage")
    public String uploadImage(@RequestParam(value = "image") MultipartFile file, HttpSession session){

        String fileName = file.getOriginalFilename();
        try {
            Uploadfile uploadFile = new Uploadfile();
            uploadFile.setName(fileName);
            uploadFile.setCreatedTime(Time.getTime());
            uploadFile.setContent(new Binary(file.getBytes()));
            uploadFile.setContentType(file.getContentType());
            uploadFile.setSize(file.getSize());
            fileRepository.save(uploadFile);
            uploadFile= FileDao.find(uploadFile,fileRepository);
            User user=(User)session.getAttribute("user");
            user.setImageid(uploadFile.getId());
            userRepository.save(user);
            session.setAttribute("user",user);


        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:/user/info";

    }

    @PostMapping("/file/uploadFile")
    public String uploadFile(@RequestParam(value = "image") MultipartFile file, HttpSession session){

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
        return "redirect:/user/info";

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


    @GetMapping(value = "/download/file/{id}")
    public String download(@PathVariable String id, HttpServletRequest request,
                           HttpServletResponse response) throws IOException {

        response.setHeader("content-type", "application/octet-stream");
        response.setContentType("application/octet-stream");
        Uploadfile file = fileRepository.findById(id).get();
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(file.getName(), "UTF-8"));
        OutputStream os = response.getOutputStream();
        os.write(file.getContent().getData());
        return "bingo";




    }




}
