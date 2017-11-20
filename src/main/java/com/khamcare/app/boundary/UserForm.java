package com.khamcare.app.boundary;

import com.khamcare.app.boundary.validator.MatchingPasswordConfirmation;
import com.khamcare.app.boundary.validator.ValidEmail;
import com.khamcare.app.model.User;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@MatchingPasswordConfirmation(groups = {UserForm.Post.class, UserForm.Put.class})
public class UserForm {

    @NotNull(groups = Post.class)
    String firstName;

    @NotNull(groups = Post.class)
    String lastName;

    @NotNull(groups = Post.class)
    @ValidEmail(groups = {Post.class, Put.class})
    String email;

    @NotNull(groups = Post.class)
    @Size(min = 6, max = 100, message = "Invalid size of password, length must be in range 6-100", groups = {Post.class, Put.class})
    String password;

    @NotNull(groups = Post.class)
    String passwordConfirmation;

    public User buildUser(){
        return User.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .password(password)
                .passwordConfirmation(passwordConfirmation)
                .build();
    }

    public interface Post {

    }

    public interface Put {

    }

}
