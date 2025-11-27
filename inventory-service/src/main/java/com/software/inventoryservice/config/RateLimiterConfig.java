package com.software.inventoryservice.config;

import com.software.inventoryservice.constants.Constant;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.time.Duration;
import java.util.Objects;

@Configuration
public class RateLimiterConfig {

    @Bean
    public Employee rajnishEmployee() {
        return new Employee("rajnish", "kumar", "rajnish@gmail.com");
    }
    @Bean
    public Employee kunalEmployee() {
        return new Employee("kunal", "kumar", "kunal@gmail.com");
    }
    @Bean
    public Bucket rateLimiter(Environment environment) {
        var limit = Bandwidth.simple(Long.parseLong(Objects.requireNonNull(environment.getProperty(Constant.numberOfRequest))),
                Duration.ofSeconds(Long.parseLong(Objects.requireNonNull(environment.getProperty(Constant.durationToConsume)))));
        return Bucket.builder().addLimit(limit).build();
    }
}
