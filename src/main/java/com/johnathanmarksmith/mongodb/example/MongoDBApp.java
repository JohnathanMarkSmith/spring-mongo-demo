package com.johnathanmarksmith.mongodb.example;

import com.johnathanmarksmith.mongodb.example.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class MongoDBApp {

    static final Logger logger = LoggerFactory.getLogger(MongoDBApp.class);

    public static void main(String[] args) {
        logger.info("MongoDemo application");

        ApplicationContext context = new AnnotationConfigApplicationContext(MongoConfiguration.class);

        PersonRepository personRepository = context.getBean(PersonRepository.class);

        // cleanup person collection before insertion
        personRepository.dropPersonCollection();

        //create person collection
        personRepository.createPersonCollection();

        for (int i = 0; i < 10000; i++) {
            personRepository.insertPersonWithNameJohnathanAndRandomAge();
        }

        personRepository.countAllPersons();
        logger.info("MongoDemo application");
    }
}
