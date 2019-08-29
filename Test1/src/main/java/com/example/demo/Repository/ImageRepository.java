package com.example.demo.Repository;

import com.example.demo.Entity.Follow;
import com.example.demo.Entity.Image;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ImageRepository extends MongoRepository<Image,String> {




}
