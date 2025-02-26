package com.example.technology;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(info = @Info(title = "Tecnologia Microservicio", version = "1.0", description = "Microservicio para gestionar las tecnologias"))
@SpringBootApplication
public class TechnologyMfApplication {

    public static void main(String[] args) {
        SpringApplication.run(TechnologyMfApplication.class, args);
    }

}
