package com.software.consumerservice.configuration;

import com.software.consumerservice.constants.Constant;
import io.github.bucket4j.Bucket;
import org.junit.jupiter.api.Test;
import org.springframework.core.env.Environment;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RateLimiterConfigTest {

    @Test
    void rateLimiterReturnsBucketConfiguredFromEnvironment() {
        Environment env = mock(Environment.class);
        when(env.getProperty(Constant.numberOfRequest)).thenReturn("10");
        when(env.getProperty(Constant.durationToConsume)).thenReturn("5");

        RateLimiterConfig cfg = new RateLimiterConfig();
        Bucket bucket = cfg.rateLimiter(env);

        assertNotNull(bucket);
        assertTrue(bucket.tryConsume(10));
        assertFalse(bucket.tryConsume(1));
    }

    @Test
    void rateLimiterThrowsWhenNumberOfRequestPropertyMissing() {
        Environment env = mock(Environment.class);
        when(env.getProperty(Constant.numberOfRequest)).thenReturn(null);
        when(env.getProperty(Constant.durationToConsume)).thenReturn("5");

        RateLimiterConfig cfg = new RateLimiterConfig();
        assertThrows(NullPointerException.class, () -> cfg.rateLimiter(env));
    }

    @Test
    void rateLimiterThrowsWhenDurationPropertyMissing() {
        Environment env = mock(Environment.class);
        when(env.getProperty(Constant.numberOfRequest)).thenReturn("10");
        when(env.getProperty(Constant.durationToConsume)).thenReturn(null);

        RateLimiterConfig cfg = new RateLimiterConfig();
        assertThrows(NullPointerException.class, () -> cfg.rateLimiter(env));
    }

    @Test
    void rateLimiterThrowsWhenPropertiesAreNonNumeric() {
        Environment env = mock(Environment.class);
        when(env.getProperty(Constant.numberOfRequest)).thenReturn("notANumber");
        when(env.getProperty(Constant.durationToConsume)).thenReturn("5");

        RateLimiterConfig cfg = new RateLimiterConfig();
        assertThrows(NumberFormatException.class, () -> cfg.rateLimiter(env));
    }

    @Test
    void rateLimiterWithZeroRequestsCreatesZeroCapacityBucket() {
        Environment env = mock(Environment.class);
        when(env.getProperty(Constant.numberOfRequest)).thenReturn("0");
        when(env.getProperty(Constant.durationToConsume)).thenReturn("5");

        RateLimiterConfig cfg = new RateLimiterConfig();
        try {
            cfg.rateLimiter(env);
            fail("Expected IllegalArgumentException for zero tokens");
        } catch (IllegalArgumentException ex) {
            assertTrue(ex.getMessage().toLowerCase().contains("wrong value") || ex.getMessage().toLowerCase().contains("tokens should be positive"));
        }
    }
}
