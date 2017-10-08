package com.khamcare.app.service;

import com.khamcare.app.model.User;
import com.khamcare.app.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    /**
     * find a user given his id
     * @param id
     * @return
     */
    public User findUserById(Long id){
        return userRepository.findOne(id);
    }

    /**
     * find a user given his email
     * @param email
     * @return
     */
    public User findUserByEmail(String email){
        return userRepository.findUserByEmail(email);
    }
}
