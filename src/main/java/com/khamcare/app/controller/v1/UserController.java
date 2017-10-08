package com.khamcare.app.controller.v1;

import com.khamcare.app.boundary.UserPostForm;
import com.khamcare.app.boundary.error.DuplicationException;
import com.khamcare.app.model.User;
import com.khamcare.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Void> createUser(@RequestBody @Valid UserPostForm userPostForm, UriComponentsBuilder uriComponentsBuilder){
        User newUser = userPostForm.buildUser();
        try {
            User user = userService.saveUser(newUser);

            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(uriComponentsBuilder.path("/api/user/{id}").buildAndExpand(user.getId()).toUri());

            return new ResponseEntity<>(headers, HttpStatus.CREATED);
        } catch (DuplicationException e){
            throw new DuplicationException("User has already existed");
        }
    }
}
