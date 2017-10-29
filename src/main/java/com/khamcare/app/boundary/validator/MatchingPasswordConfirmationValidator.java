package com.khamcare.app.boundary.validator;


import com.khamcare.app.boundary.UserForm;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MatchingPasswordConfirmationValidator implements ConstraintValidator <MatchingPasswordConfirmation, UserForm> {

    @Override
    public void initialize(MatchingPasswordConfirmation matchingPasswordConfirmation){

    }

    @Override
    public boolean isValid(UserForm userForm, ConstraintValidatorContext cxt){
        if (userForm.getPassword()==null && userForm.getPasswordConfirmation() == null) return true;

        return (userForm.getPasswordConfirmation().equals(userForm.getPassword()));
    }

}
