package com.khamcare.app.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MongoProperties {

    @Value("${spring.data.mongodb.host}")
    private String mongoDbHost;

    @Value("${spring.data.mongodb.port}")
    private Integer mongoDbPort;

    @Value("${spring.data.mongodb.database}")
    private String mongoDbDatabase;

    public String getHost(){ return mongoDbHost; }

    public Integer getPort(){ return mongoDbPort; }

    public String getDatabase(){ return mongoDbDatabase;}

}
