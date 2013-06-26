package com.johnathanmarksmith.mongodb.example;

import com.mongodb.Mongo;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;


/**
 * Date:   5/24/13 / 8:05 AM
 * Author: Johnathan Mark Smith
 * Email:  john@johnathanmarksmith.com
 * <p/>
 * Comments:
 * <p/>
 * This is a example on how to setup a database with Spring's Java Configuration (JavaConfig) style.
 * <p/>
 * As you can see from the code below this is easy and a lot better then using the old style of XML files.
 * <p/>
 * This is used to read in a properties file and setup access to the RESTServer bean/
 */

@Configuration
@EnableMongoRepositories
@ComponentScan(basePackageClasses = {MongoDBApp.class})
@PropertySource("classpath:application.properties")
public class MongoConfiguration extends AbstractMongoConfiguration
{


    @Override
    protected String getDatabaseName() {
        return "demo";
    }

    @Override
    public Mongo mongo() throws Exception {
        return new Mongo();
    }

    @Override
    protected String getMappingBasePackage() {
        return "com.johnathanmarksmith.mongodb.example.domain";
    }

}
