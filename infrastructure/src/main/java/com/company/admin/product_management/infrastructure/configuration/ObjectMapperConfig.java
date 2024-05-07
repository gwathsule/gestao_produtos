package com.company.admin.product_management.infrastructure.configuration;

import com.company.admin.product_management.infrastructure.configuration.json.Json;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;

public class ObjectMapperConfig {

    @Bean
    public ObjectMapper objectMapper() {
        return Json.mapper();
    }
}
