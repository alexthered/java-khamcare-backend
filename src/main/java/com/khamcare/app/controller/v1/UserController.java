package com.khamcare.app.controller.v1;

import com.khamcare.app.boundary.UserForm;
import com.khamcare.app.boundary.error.DuplicationException;
import com.khamcare.app.boundary.error.NotFoundException;
import com.khamcare.app.model.User;
import com.khamcare.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;


@RestController
@RequestMapping("/v1/users")
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<User> getUser(@PathVariable("id") String id){
        User user = userService.findUserById(id);

        if (user == null){
            throw new NotFoundException(String.format("User entity cannot be found {id=%s}.", id));
        }

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> createUser(@RequestBody @Validated(UserForm.Post.class) UserForm userForm, UriComponentsBuilder uriComponentsBuilder){
        User newUser = userForm.buildUser();
        try {
            User user = userService.insertUser(newUser);

            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(uriComponentsBuilder.path("/v1/users/{id}").buildAndExpand(user.getId()).toUri());

            return new ResponseEntity<>(headers, HttpStatus.CREATED);
        } catch (DuplicationException e){
            throw new DuplicationException("User has already existed");
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<User> updateUser(@PathVariable("id") String id, @RequestBody @Validated(UserForm.Put.class) UserForm userForm) {
        User user = userService.findUserById(id);

        if (user == null){
            throw new NotFoundException(String.format("User entity cannot be found {id=%s}.", id));
        }

        User updatedUser = userService.updateUser(user, userForm);

        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteUser(@PathVariable("id") String id){
        User user = userService.findUserById(id);

        if (user == null){
            throw new NotFoundException(String.format("User entity cannot be found {id=%s}.", id));
        }

        userService.deleteUser(user);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
