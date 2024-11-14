package com.yrgo.advice;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.aspectj.lang.ProceedingJoinPoint;

public class PerformanceTimingAdvice {

    public Object performTimingMeasurement (ProceedingJoinPoint method) throws Throwable {
        // Before
        long startTime = System.nanoTime();

        try {
            // Proceed to target
            Object value = method.proceed();
            return value;
        } finally {
            // After
            long endTime = System.nanoTime();
            long timeTaken = endTime - startTime;
            System.out.println("Time taken for the method " + "[" + method.getSignature().getName() + "]" +
                               " from the class " + method.getSourceLocation().getWithinType().getName() +
                               " took " + (timeTaken / 1_000_000.0) + " ms");
        }
    }
}