package com.khamcare.app.request;

import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.when;
import static org.hamcrest.CoreMatchers.equalTo;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("it-embedded")
@EnableAutoConfiguration
public class HealthControllerTest{

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