package com.example.demo.Repository;

import com.example.demo.Entity.Follow;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FollowRepository extends MongoRepository<Follow,String> {
}
