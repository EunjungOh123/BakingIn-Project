package com.zerobase.bakingin_project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class BakingInProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(BakingInProjectApplication.class, args);
    }
}
