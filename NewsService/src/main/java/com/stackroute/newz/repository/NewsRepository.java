package com.stackroute.newz.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.stackroute.newz.model.UserNews;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsRepository extends MongoRepository<UserNews, String> {
}
