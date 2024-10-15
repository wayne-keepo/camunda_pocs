package com.example.workflow;

import com.example.workflow.service.CamundaProcessingService;
import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableProcessApplication
@SpringBootApplication
@Configuration
public class Application {

  public static void main(String... args) {
    SpringApplication.run(Application.class, args);
  }

  @Bean
  public CamundaProcessingService camundaProcessingService() {
    return new CamundaProcessingService();
  }

}