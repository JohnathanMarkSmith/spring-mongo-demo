package com.johnathanmarksmith.mongodb.example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.mongodb.core.MongoFactoryBean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.transaction.annotation.EnableTransactionManagement;


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
@EnableTransactionManagement
@ComponentScan(basePackageClasses = {MongoDBApp.class})
@PropertySource("classpath:application.properties")
public class MongoConfiguration
{

    <!-- Define the MongoTemplate which handles connectivity with MongoDB -->
    <bean id="mongoTemplate" class="org.springframework.data.mongodb.core.MongoTemplate">
    <constructor-arg name="mongo" ref="mongo"/>
    <constructor-arg name="databaseName" value="demo"/>
    </bean>

    @Bean
    public MongoTemplate mongoTemplate()
    {
        MongoTemplate tp = new MongoTemplate(mongo(), "demo");
        return tp;
    }

    @Bean
    public MongoFactoryBean mongo(){
        MongoFactoryBean mfb = new MongoFactoryBean();
        mfb.setHost("localhost");

        return mfb;
    }

}
