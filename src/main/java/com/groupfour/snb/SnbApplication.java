package com.groupfour.snb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class SnbApplication {
    public static void main(String[] args) {
        SpringApplication.run(SnbApplication.class, args);
    }

    @GetMapping()
    public String mainMenu(){
        return "Welcome";
    }
}
