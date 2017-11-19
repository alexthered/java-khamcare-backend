package com.khamcare.app.config;

import com.mongodb.MongoClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

@Slf4j
@Configuration
@Profile("!it-embedded")
public class MongoConfigProfile {

    @Autowired
    private MongoProperties mongoProperties;

    @Bean
    public MongoDbFactory mongoDbFactory() throws Exception {
        log.info(String.format("Setting up connection to Mongo Db at %s:%d/%s", mongoProperties.getHost(), mongoProperties.getPort(), mongoProperties.getDatabase()));
        MongoClient mongoClient = new MongoClient(mongoProperties.getHost(), mongoProperties.getPort());
        return new SimpleMongoDbFactory(mongoClient, mongoProperties.getDatabase());
    }

    @Bean
    public MongoTemplate mongoTemplate() throws Exception {
        MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory());
        return mongoTemplate;
    }
}
