package com.khamcare.app.boundary.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = MatchingPasswordConfirmationValidator.class)
@Target({ ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface MatchingPasswordConfirmation {
    String message() default "Password confirmation does not match";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
