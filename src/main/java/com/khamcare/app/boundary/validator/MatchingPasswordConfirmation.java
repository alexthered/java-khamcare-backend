package com.khamcare.app.boundary.validator;

import javax.validation.Constraint;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = MatchingPasswordConfirmationValidator.class)
@Target({ ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface MatchingPasswordConfirmation {
}
