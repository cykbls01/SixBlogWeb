package com.example.demo.Repository;

import com.example.demo.Entity.Blog;
import com.example.demo.Entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BlogRepository extends MongoRepository<Blog,String> {
}
