package com.example.chat_application.repository;

import com.example.chat_application.model.Group;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends MongoRepository<Group, String> {
    // MongoRepository automatically provides CRUD methods
}
