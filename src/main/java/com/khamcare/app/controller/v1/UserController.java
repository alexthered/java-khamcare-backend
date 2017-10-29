package com.khamcare.app.controller.v1;

import com.khamcare.app.boundary.UserForm;
import com.khamcare.app.boundary.error.DuplicationException;
import com.khamcare.app.model.User;
import com.khamcare.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

@RestController
@RequestMapping("/v1/users")
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public ResponseEntity<User> getUser(@PathVariable("userId") Long userId){
        User user = userService.findUserById(userId);

        if (user == null){
            throw new EntityNotFoundException(String.format("User entity cannot be found {id=%d}.", userId));
        }

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> createUser(@RequestBody @Validated(UserForm.Post.class) UserForm userForm, UriComponentsBuilder uriComponentsBuilder){
        User newUser = userForm.buildUser();
        try {
            User user = userService.saveUser(newUser);

            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(uriComponentsBuilder.path("/api/user/{id}").buildAndExpand(user.getId()).toUri());

            return new ResponseEntity<>(headers, HttpStatus.CREATED);
        } catch (DuplicationException e){
            throw new DuplicationException("User has already existed");
        }
    }

    @RequestMapping(value = "/{userId}", method = RequestMethod.PUT)
    public ResponseEntity<User> updateUser(@PathVariable("userId") Long userId, @RequestBody @Validated(UserForm.Put.class) UserForm userForm) {
        User user = userService.findUserById(userId);

        if (user == null){
            throw new EntityNotFoundException(String.format("User entity cannot be found {id=%d}.", userId));
        }

        user = userForm.mergeUser(user);

        User updatedUser = userService.saveUser(user);

        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @RequestMapping(value = "/{userId}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteUser(@PathVariable("userId") Long userId){
        User user = userService.findUserById(userId);

        if (user == null){
            throw new EntityNotFoundException(String.format("User entity cannot be found {id=%d}.", userId));
        }

        userService.deleteUser(user);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
