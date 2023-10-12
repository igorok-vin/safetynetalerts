package com.safetynet.safetynetalerts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.trace.http.HttpTraceRepository;
import org.springframework.boot.actuate.trace.http.InMemoryHttpTraceRepository;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class SafetyNetAlertsApp {

    public static void main(String[] args) {
        SpringApplication.run(SafetyNetAlertsApp.class, args);
    }

    @Bean
    public HttpTraceRepository httpTraceRepository() {

        return new InMemoryHttpTraceRepository();
    }
}