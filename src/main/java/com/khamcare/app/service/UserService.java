package com.khamcare.app.service;

import com.khamcare.app.boundary.UserForm;
import com.khamcare.app.boundary.error.DuplicationException;
import com.khamcare.app.model.User;
import com.khamcare.app.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

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
    public User insertUser(User user) throws DuplicationException {
        try {
            //encode the password
            user.setPassword(passwordEncoder.encode(user.getPassword()));

            return userRepository.save(user);
        } catch (DataIntegrityViolationException e){
            throw new DuplicationException(e.getMessage());
        }
    }

    /**
     *
     * @param user
     * @param userForm
     * @return
     */
    public User updateUser(User user, UserForm userForm){

        //only update the field if it's not null
        if (userForm.getFirstName() != null) user.setFirstName(userForm.getFirstName());
        if (userForm.getLastName() != null) user.setLastName(userForm.getLastName());
        if (userForm.getEmail() != null) user.setFirstName(userForm.getEmail());
        if (userForm.getPassword() != null){
            user.setPassword(passwordEncoder.encode(userForm.getPassword()));
        }

        return userRepository.save(user);
    }

    /**
     *
     * @param user
     */
    public void deleteUser(User user){
        userRepository.delete(user);
    }
}
