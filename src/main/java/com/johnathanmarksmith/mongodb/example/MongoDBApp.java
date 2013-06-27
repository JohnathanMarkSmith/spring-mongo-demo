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

        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            personRepository.insertPersonWithNameJohnathanAndRandomAge();
        }
        long endTime = System.currentTimeMillis();
        logger.info("Load Took " + (endTime - startTime) / 1000 + " seconds");


        startTime = System.currentTimeMillis();
        personRepository.countAllPersons();
        endTime = System.currentTimeMillis();
        logger.info("Count All Took " + (endTime - startTime)  / 1000 + " seconds");


        /***
         *
         * Added Under Age Test For someone to see
         *
         */
        startTime = System.currentTimeMillis();
        personRepository.countUnderAge();
        endTime = System.currentTimeMillis();
        logger.info("Under age search Took " + (endTime - startTime) / 1000 + " seconds");




        logger.info("MongoDemo application");
    }
}
