package com.example.demo.Repository;

import com.example.demo.Entity.Uploadfile;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FileRepository extends MongoRepository<Uploadfile,String> {
}
