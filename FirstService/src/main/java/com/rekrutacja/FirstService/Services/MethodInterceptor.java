package com.rekrutacja.FirstService.Services;

import com.sun.management.OperatingSystemMXBean;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.lang.management.ManagementFactory;
import java.util.Arrays;

@Aspect
@Component
public class MethodInterceptor {

    private Object interceptedResult;
    private final OperatingSystemMXBean os = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
    @Pointcut("execution(* com.rekrutacja.FirstService.Services.FirstService.generateJsonList(int)) && args(size)")
    public void generateJsonListExecution(int size) {}

    @Around("generateJsonListExecution(size)")
    public Object monitorGenerateJsonList(ProceedingJoinPoint joinPoint, int size) throws Throwable {
        long startTime = System.nanoTime();

        // Proceed with the original method call
        Object result = joinPoint.proceed();
        double cpuload = os.getProcessCpuLoad() * 100;
        long endTime = System.nanoTime();
        long executionTime = endTime - startTime;
        double executionTimeInmSec = executionTime/1000000;

        // Log method invocation details
        System.out.println("Method generateJsonList executed with size " + size);
        System.out.println("cpu load  " + cpuload);
        System.out.println("memory size used in GB  " + (double) os.getFreeMemorySize()/1000000000);
        System.out.println("Execution Time: " + executionTimeInmSec + " miliseconds");


        return interceptedResult;
    }


}



