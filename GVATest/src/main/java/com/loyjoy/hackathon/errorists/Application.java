package com.loyjoy.hackathon.errorists;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.mongodb.core.MongoExceptionTranslator;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan
@PropertySource(value = "classpath:application.properties")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    
    @Bean 
    public MongoExceptionTranslator mongoExceptionTranslator(){ 
    	return new MongoExceptionTranslator(); 
    }
}
