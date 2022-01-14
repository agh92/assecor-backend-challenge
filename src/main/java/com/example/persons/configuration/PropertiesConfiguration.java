package com.example.persons.configuration;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PropertiesConfiguration {
    @Getter
    @Value("${file_path}")
    private String csvLocation;
}
