package com.jb.cli;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main Spring Boot application used to launch the Command Shell CLI
 */
@SpringBootApplication
public class SmsCliApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(SmsCliApplication.class, args);
    }

    @Override
    public void run(String... args) {
    }


}

