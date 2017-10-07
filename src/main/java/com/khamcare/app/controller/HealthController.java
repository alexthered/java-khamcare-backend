package com.khamcare.app.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

    @RequestMapping("/health")
    @ResponseStatus(HttpStatus.OK)
    public String getHealth(){
        return "All is well";
    }
}
