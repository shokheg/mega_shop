package com.amr.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.session.SessionAutoConfiguration;


@SpringBootApplication(exclude = {SessionAutoConfiguration.class,
                                  SecurityAutoConfiguration.class})
public class ProjectApplication {
    public static void main(String[] args){
        SpringApplication.run(ProjectApplication.class, args);
    }
}
