package com.khamcare.app.boundary;

import com.khamcare.app.boundary.validation.ValidEmail;
import com.khamcare.app.model.User;
import lombok.Data;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class UserPostForm {

    @NotNull
    String firstName;

    @NotNull
    String lastName;

    @NotNull
    @ValidEmail
    String email;

    @NotNull
    @Size(min = 6, max = 100)
    String password;

    @NotNull
    @Size(min = 6, max = 100)
    String passwordConfirmation;

    //assert that the password and password confirmation must match each other
    @AssertTrue(message = "does not match password")
    public boolean isPasswordConfirmationMatch() {
        return password.equals(passwordConfirmation);
    }

    public User buildUser(){
        return User.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .password(password)
                .passwordConfirmation(passwordConfirmation)
                .build();
    }

}
