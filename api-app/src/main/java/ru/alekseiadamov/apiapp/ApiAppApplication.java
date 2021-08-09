package ru.alekseiadamov.apiapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages= {"ru.alekseiadamov"})
public class ApiAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiAppApplication.class, args);
    }

}
