package com.software.courseservice.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.context.annotation.Configuration;

@Setter
@Getter
@ToString
@Configuration
public class ApiConfiguration {
    private String key;
    private String token;
}
