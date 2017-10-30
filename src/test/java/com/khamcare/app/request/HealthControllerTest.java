package com.khamcare.app.request;

import com.khamcare.app.AppApplication;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


import static io.restassured.RestAssured.when;
import static org.hamcrest.CoreMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HealthControllerTest extends BaseControllerTestHelper {

    @LocalServerPort
    private int port;

    @Before
    public void setup(){
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }
    
    @Test
    public void getHealth() throws Exception {

        when().get("/health").then().log().ifValidationFails()
                .assertThat().statusCode(200)
                .body(equalTo("All is well"));
    }
}