package com.nikkodasig.springbudgetapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SpringBudgetApiApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpringBudgetApiApplication.class, args);
  }

}
