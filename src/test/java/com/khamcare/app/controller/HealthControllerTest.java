package com.khamcare.app.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@WebMvcTest(value = HealthController.class, secure = false)
public class HealthControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void getHealth() throws Exception {
        RequestBuilder healthRequestBuilder = MockMvcRequestBuilders.get(
                "/health");

        MvcResult result = mockMvc.perform(healthRequestBuilder).andReturn();

        assertEquals("the response's status is 200", 200, result.getResponse().getStatus());
        assertEquals("All is well", result.getResponse()
                .getContentAsString());
    }

}