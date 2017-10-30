package com.khamcare.app.request;

import br.com.six2six.fixturefactory.Fixture;
import com.khamcare.app.AppApplication;
import com.khamcare.app.boundary.UserForm;
import com.khamcare.app.model.User;
import com.khamcare.app.repository.UserRepository;
import com.khamcare.app.util.RandomString;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;
import static io.restassured.RestAssured.get;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest extends BaseControllerTestHelper {

    User user;
    UserForm userForm;

    @Autowired
    UserRepository userService;

    @LocalServerPort
    private int port;

    @Before
    public void setup(){
        // given
        user = Fixture.from(User.class).gimme("valid");

        userForm = new UserForm();
        userForm.setFirstName(new RandomString(10).nextString());
        userForm.setLastName(new RandomString(10).nextString());
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

        get("/v1/users/1000")
                .then()
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
    public void testInsertUserWithNoFirstName() throws Exception {

    }
}
