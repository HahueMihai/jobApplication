package com.jobApplication.jobApplication;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public GroupedOpenApi medicationApi() {
        return GroupedOpenApi.builder()
                .group("Medications")
                .pathsToMatch("/api/medication/**", "/api/patients/**")
                .build();
    }

}
