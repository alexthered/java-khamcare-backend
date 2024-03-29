package com.khamcare.app.boundary.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * valid email validator
 */
public class ValidEmailValidator implements
        ConstraintValidator<ValidEmail, String> {

    @Override
    public void initialize(ValidEmail email) {
    }

    @Override
    public boolean isValid(String email,
                           ConstraintValidatorContext cxt) {
        //not null email is handled differently
        if (email == null) return true;
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        Pattern p = java.util.regex.Pattern.compile(ePattern);
        Matcher m = p.matcher(email);
        return m.matches();
    }

}