package io.java.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource({"classpath*:spring/spring-config.xml"})
public class CourseApiApp {

    public static void main(String[] args) {
        SpringApplication.run(CourseApiApp.class, args);
    }


}
