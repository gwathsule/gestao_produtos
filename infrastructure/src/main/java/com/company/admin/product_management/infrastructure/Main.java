package com.company.admin.product_management.infrastructure;

import com.company.admin.product_management.infrastructure.configuration.WebServerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.AbstractEnvironment;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        System.out.println("Hello and welcome!");
        System.setProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME, "development");
        SpringApplication.run(WebServerConfig.class, args);
    }
}