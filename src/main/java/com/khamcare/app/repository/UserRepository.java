package com.khamcare.app.repository;

import com.khamcare.app.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    User findUserByEmail(String email);
}
