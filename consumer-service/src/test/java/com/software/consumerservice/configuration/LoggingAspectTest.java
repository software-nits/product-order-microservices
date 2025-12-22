package com.software.consumerservice.configuration;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LoggingAspectTest {

    @Test
    void logAroundReturnsResultFromProceedingJoinPoint() throws Throwable {
        ProceedingJoinPoint pjp = mock(ProceedingJoinPoint.class);
        Signature sig = mock(Signature.class);
        when(sig.getName()).thenReturn("sampleMethod");
        when(pjp.getArgs()).thenReturn(new Object[]{"arg1", 2});
        when(pjp.getSignature()).thenReturn(sig);
        when(pjp.getTarget()).thenReturn(this);
        when(pjp.proceed()).thenReturn("expectedResult");

        LoggingAspect aspect = new LoggingAspect();

        Object result = aspect.logAround(pjp);

        assertEquals("expectedResult", result);
        verify(pjp, times(1)).proceed();
        verify(pjp, atLeastOnce()).getArgs();
        verify(pjp, atLeastOnce()).getSignature();
        verify(pjp, atLeastOnce()).getTarget();
    }

    @Test
    void logAroundPropagatesThrowableFromProceed() throws Throwable {
        ProceedingJoinPoint pjp = mock(ProceedingJoinPoint.class);
        Signature sig = mock(Signature.class);
        when(sig.getName()).thenReturn("sampleMethod");
        when(pjp.getArgs()).thenReturn(new Object[]{});
        when(pjp.getSignature()).thenReturn(sig);
        when(pjp.getTarget()).thenReturn(this);
        when(pjp.proceed()).thenThrow(new RuntimeException("boom"));

        LoggingAspect aspect = new LoggingAspect();

        assertThrows(RuntimeException.class, () -> aspect.logAround(pjp));
        verify(pjp, times(1)).proceed();
    }
}

