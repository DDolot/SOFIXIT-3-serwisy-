//package com.rekrutacja.CsvConverter;
//import io.micrometer.core.instrument.MeterRegistry;
//import io.micrometer.core.instrument.Timer;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.springframework.stereotype.Component;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.concurrent.CompletableFuture;
//import java.util.concurrent.ExecutionException;
//import java.util.concurrent.Executors;
//import java.util.concurrent.ScheduledExecutorService;
//import java.util.concurrent.TimeUnit;
//
//@Aspect
//@Component
//public class Aspect3 {
//
//    private final MeterRegistry registry;
//    private final Timer timer;
//
//    public Aspect3(final MeterRegistry registry) {
//        this.registry = registry;
//        this.timer = registry.timer("fetching.data.timer");
//    }
//
//    @Around("execution(* com.rekrutacja.CsvConverter.Services.CsvConverterService.convertToCSV(..))")
//    public Object around(ProceedingJoinPoint jp) throws Throwable {
//        List<Double> cpuUsages = new ArrayList<>();
//        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
//
//        CompletableFuture<Object> future = CompletableFuture.supplyAsync(() -> timer.record(() -> {
//            try {
//                return jp.proceed();
//            } catch (Throwable e) {
//                throw new RuntimeException(e);
//            }
//        }));
//
//
//        Runnable cpuUsageTask = () -> cpuUsages.add(getCpuUsage());
//
//        // Schedule the CPU usage task to run periodically
//        scheduler.scheduleAtFixedRate(cpuUsageTask, 50, 100, TimeUnit.MILLISECONDS);
//
//        Object result = null;
//        try {
//            result = future.get();
//        } finally {
//            // Stop the scheduler
//            scheduler.shutdown();
//            // Await termination of the scheduler
//            if (!scheduler.awaitTermination(1, TimeUnit.SECONDS)) {
//                scheduler.shutdownNow();
//            }
//        }
//
//        logCpuUsages(cpuUsages);
//
//        return result;
//    }
//
//    private double getCpuUsage() {
//        return registry.get("system.cpu.usage").gauge().value();
//    }
//
//    private void logCpuUsages(List<Double> cpuUsages) {
//        System.out.println("CPU usages during method execution: " + cpuUsages);
//        // Optionally, register these values with your MeterRegistry or other logging mechanism
//    }
//}
