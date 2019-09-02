package com.example.subscribeUsapp.repository;

import com.example.subscribeUsapp.entity.Users;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface MongoRepository extends ReactiveMongoRepository<Users, String> {

}
