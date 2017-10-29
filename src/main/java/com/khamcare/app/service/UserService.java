package com.khamcare.app.service;

import com.khamcare.app.boundary.UserForm;
import com.khamcare.app.boundary.error.DuplicationException;
import com.khamcare.app.model.User;
import com.khamcare.app.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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

    /**
     *
     * @param user
     * @return
     */
    public User saveUser(User user) throws DuplicationException {
        try {
            return userRepository.save(user);
        } catch (DataIntegrityViolationException e){
            throw new DuplicationException(e.getMessage());
        }
    }

    /**
     *
     * @param user
     */
    public void deleteUser(User user){
        userRepository.delete(user);
    }
}
