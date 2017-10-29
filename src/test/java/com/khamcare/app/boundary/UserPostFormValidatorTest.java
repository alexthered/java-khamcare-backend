package com.khamcare.app.boundary;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import com.khamcare.app.util.RandomString;
import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class UserPostFormValidatorTest {

    @Before
    public void setUp() throws Exception {
        FixtureFactoryLoader.loadTemplates("com.khamcare.app.fixture");
    }

    private Validator createValidator(){
        LocalValidatorFactoryBean validatorFactoryBean = new LocalValidatorFactoryBean();
        validatorFactoryBean.afterPropertiesSet();

        return validatorFactoryBean;
    }

    @Test
    public void testValidUserPostForm() throws Exception {
        UserPostForm userPostForm = Fixture.from(UserPostForm.class).gimme("valid");

        Validator validator = createValidator();
        Set<ConstraintViolation<UserPostForm>> constraintViolations = validator.validate(userPostForm);

        assertTrue("there is no constraint violation", constraintViolations.size() == 0);
    }

    @Test
    public void testInvalidUserPostFormWhenPasswordDoesNotMatch() throws Exception {
        UserPostForm userPostForm = Fixture.from(UserPostForm.class).gimme("valid");

        userPostForm.setPasswordConfirmation(userPostForm.password + new RandomString(2).nextString());

        Validator validator = createValidator();
        Set<ConstraintViolation<UserPostForm>> constraintViolations = validator.validate(userPostForm);

        assertEquals("there is 1 constrain violation", 1, constraintViolations.size());

        //check if the error message is correct
        ConstraintViolation<UserPostForm> violation = constraintViolations.iterator().next();
        assertTrue(violation.getPropertyPath().toString().equals("passwordConfirmationMatch"));
        assertTrue(violation.getMessage().equals("does not match password"));
    }

}
