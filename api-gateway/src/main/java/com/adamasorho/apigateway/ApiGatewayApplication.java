package com.adamasorho.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApiGatewayApplication {

    // todo: update distributed tracing for spring boot 3. Follow the link below
    // https://spring.io/blog/2022/10/12/observability-with-spring-boot-3

    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }
}
