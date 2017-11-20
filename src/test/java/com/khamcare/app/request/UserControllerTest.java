package com.khamcare.app.request;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import com.khamcare.app.boundary.UserForm;
import com.khamcare.app.model.User;
import com.khamcare.app.repository.UserRepository;
import com.khamcare.app.util.RandomString;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Field;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("it-embedded")
@EnableAutoConfiguration
public class UserControllerTest {

    User user;
    UserForm userForm;

    @Autowired
    UserRepository userService;

    @LocalServerPort
    private int port;

    @BeforeClass
    public static void init() throws Exception {
        FixtureFactoryLoader.loadTemplates("com.khamcare.app.fixture");
    }

    @Before
    public void setup(){
        // given
        user = Fixture.from(User.class).gimme("valid");

        userForm = new UserForm();
        userForm.setFirstName(user.getFirstName());
        userForm.setLastName(user.getLastName());
        userForm.setPassword(new RandomString(10).nextString());
        userForm.setPasswordConfirmation(userForm.getPassword());
        userForm.setEmail(user.getFirstName() + "." + user.getLastName() + "@gmail.com");

        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }

    @Test
    public void testGetUser() throws Exception {

        //save user to database
        userService.save(user);

        User retrievedUser = get("/v1/users/" + user.getId())
                .then().log().ifValidationFails().statusCode(200)
                .extract().as(User.class);

        assertNotNull("retrieve user must not be null", retrievedUser);
        assertEquals("retrieved user must have same Id", user.getId(), retrievedUser.getId());

        assertTrue("password field must not be sent to client", retrievedUser.getPassword() == null);
    }

    @Test
    public void testGetNonExistingUserShouldReturn404Code() throws Exception {

        get("/v1/users/0000000")
                .then().log().ifValidationFails()
                .statusCode(404)
                .body(containsString("User entity cannot be found"));
    }

    @Test
    public void testInsertValidUserForm() throws Exception {
        Response response = given()
                .contentType(ContentType.JSON)
                .body(userForm)
                .post("/v1/users")
                .then().log().ifValidationFails().statusCode(201)
                .extract().response();

        // Get location header
        String location = response.getHeader("location");

        assertTrue("location must point to correct entity url", location.contains("/v1/users/"));
    }

    @Test
    public void testInsertUserWithNullField() throws Exception {
        String[] array = new String[]{"firstName", "lastName", "password", "email"};

        //go through each element in the array and set it to null
        for (int i=0; i<array.length; i++) {
            String fieldName = array[i];

            Field field = userForm.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            Object value = field.get(userForm);

            field.set(userForm, null);
            given()
                    .contentType(ContentType.JSON)
                    .body(userForm)
                    .post("/v1/users")
                    .then().log().ifValidationFails().statusCode(400)
                    .body(containsString(String.format("%s: may not be null", fieldName)));

            //set back the old value
            field.set(userForm, value);
        }
    }

    @Test
    public void testInsertUserWithTooShortPwd() throws Exception {
        userForm.setPassword(new RandomString(3).nextString());
        userForm.setPasswordConfirmation(userForm.getPassword());
        given()
                .contentType(ContentType.JSON)
                .body(userForm)
                .post("/v1/users")
                .then().log().ifValidationFails().statusCode(400)
                .body(containsString("Invalid size of password"));
    }

    @Test
    public void testInsertUserWithNoMatchPwdAndPwdConfirmation() throws Exception {

        userForm.setPasswordConfirmation(userForm.getPassword() + new RandomString(4).nextString());
        given()
                .contentType(ContentType.JSON)
                .body(userForm)
                .post("/v1/users")
                .then().log().ifValidationFails().statusCode(400)
                .body(containsString("Password confirmation does not match"));
    }

    @Test
    public void testInsertUserWithInvalidEmailAddress() throws Exception {
        userForm.setEmail("invalidEmail.com");

        given()
                .contentType(ContentType.JSON)
                .body(userForm)
                .post("/v1/users")
                .then().log().ifValidationFails().statusCode(400)
                .body(containsString("Invalid email"));
    }

    @Test
    public void testInsertDuplicatedEmail() throws Exception {
        //first time the insertion should be successful
        given()
                .contentType(ContentType.JSON)
                .body(userForm)
                .post("/v1/users")
                .then().log().ifValidationFails().statusCode(201);

        //when inserting the user with same email address
        given()
                .contentType(ContentType.JSON)
                .body(userForm)
                .post("/v1/users")
                .then().log().ifValidationFails().statusCode(409)
                .body(containsString("User has already existed"));

    }

    @Test
    public void testUpdateNonExistingUser() throws Exception {
        UserForm userForm = new UserForm();

        given()
                .contentType(ContentType.JSON)
                .body(userForm)
                .put("/v1/users/0000000")
                .then().log().ifValidationFails()
                .statusCode(404)
                .body(containsString("User entity cannot be found"));

    }


    @Test
    public void testUpdateUserWithValidForm() throws Exception {
        //save user to database
        userService.save(user);

        //new valid user's information
        UserForm userForm = getValidUserFormInfo();
        User updatedUser = given()
                .contentType(ContentType.JSON)
                .body(userForm)
                .put("/v1/users/" + user.getId())
                .then().log().ifValidationFails()
                .statusCode(200)
                .extract().as(User.class);

        //assert user's information is updated
        assertEquals("user's id is still remained", user.getId(), updatedUser.getId());
        assertEquals("firstName is updated", userForm.getFirstName(),updatedUser.getFirstName());
        assertEquals("lastName is updated", userForm.getLastName(),updatedUser.getLastName());
        assertEquals("email is updated", userForm.getEmail(),updatedUser.getEmail());


    }



    @Test
    public void testDeleteUser() throws  Exception{

        //given: user is saved to database
        userService.save(user);

        //when deleting the user
        given()
                .contentType(ContentType.JSON)
                .delete(String.format("/v1/users/%s", user.getId()))
                .then().log().ifValidationFails().statusCode(204);
    }

    @Test
    public void testDeleteNonExistingUser() throws Exception {
        //when deleting the user
        given()
                .contentType(ContentType.JSON)
                .delete(String.format("/v1/users/%s", new RandomString(10).toString() ))
                .then().log().ifValidationFails().statusCode(404);
    }


    private UserForm getValidUserFormInfo() {
        User user = Fixture.from(User.class).gimme("valid");

        UserForm userForm = new UserForm();
        userForm.setEmail(user.getEmail());
        userForm.setFirstName(user.getFirstName());
        userForm.setLastName(user.getLastName());
        userForm.setPassword(user.getPassword());
        userForm.setPasswordConfirmation(user.getPassword());

        return userForm;

    }
    
}
