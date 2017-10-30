package com.khamcare.app.boundary;

import com.khamcare.app.boundary.validator.MatchingPasswordConfirmation;
import com.khamcare.app.model.User;
import com.khamcare.app.boundary.validator.ValidEmail;
import lombok.Data;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@MatchingPasswordConfirmation
public class UserForm {

    @NotNull(groups = Post.class)
    String firstName;

    @NotNull(groups = Post.class)
    String lastName;

    @NotNull(groups = Post.class)
    @ValidEmail
    String email;

    @NotNull(groups = Post.class)
    @Size(min = 6, max = 100)
    String password;

    @NotNull(groups = Post.class)
    @Size(min = 6, max = 100)
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
