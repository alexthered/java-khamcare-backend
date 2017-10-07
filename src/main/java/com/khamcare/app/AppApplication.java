package com.khamcare.app;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.sql.DataSource;

@Slf4j
@SpringBootApplication
public class AppApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(AppApplication.class, args);

    }

}
