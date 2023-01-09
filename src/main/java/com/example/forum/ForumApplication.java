package com.example.forum;

import com.example.forum.controllers.ThreadController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import java.io.File;

@SpringBootApplication
public class ForumApplication {

    public static void main(String[] args) {
        new File(ThreadController.uploadDir).mkdir();
        SpringApplication.run(ForumApplication.class, args);
    }

}
