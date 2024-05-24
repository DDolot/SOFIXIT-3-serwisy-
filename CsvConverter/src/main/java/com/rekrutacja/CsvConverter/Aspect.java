package com.rekrutacja.CsvConverter;

import com.fasterxml.jackson.core.ObjectCodec;
import com.rekrutacja.CsvConverter.DTOs.MeasurementDTO;
import com.rekrutacja.CsvConverter.Services.Measurement;
import com.rekrutacja.CsvConverter.Services.MeasurementActuator;
import io.micrometer.core.instrument.DistributionSummary;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import io.micrometer.core.instrument.binder.BaseUnits;
import io.micrometer.core.instrument.binder.system.ProcessorMetrics;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.lang.management.ManagementFactory;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import com.sun.management.OperatingSystemMXBean;

@org.aspectj.lang.annotation.Aspect
@Component
public class Aspect {

    private final Timer timer;
    private final Timer timerMethods;

    private final static Logger logger = LoggerFactory.getLogger(Aspect.class);
    private MeterRegistry registry;

    private final String serviceMethods = "execution(* com.rekrutacja.CsvConverter.Services.*.convertToCSV(..))";
    private Measurement measurement;
    private List<Double> cpus = new ArrayList<>();
    private List<Long> memory = new ArrayList<>();
    private OperatingSystemMXBean OSBEAN = (com.sun.management.OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();

    public Aspect(MeterRegistry registry,Measurement measurement) {
        this.measurement = measurement;
        this.timer = registry.timer("fetch.data.from.first.service");
        this.timerMethods = registry.timer("execute.service.methods");
        this.registry = registry;


    }

    @Around("execution(* com.rekrutacja.CsvConverter.clients.*.fetchJsonsFromFirstService(..))")
    public Object aroundC(ProceedingJoinPoint jp) throws Throwable {
        CompletableFuture<Object> futureProcessMethod = CompletableFuture
                .supplyAsync(() -> {
                    try {
                        return jp.proceed();
                    } catch (Throwable e) {
                        throw new RuntimeException(e);
                    }
                });
        MeasurementDTO measurementDTO = measurement.takeMeasurement(futureProcessMethod);
        System.out.println(measurementDTO);
        return futureProcessMethod.get();
    }
}
//
//
//
//
//
//
//
////    @Around("execution(* com.rekrutacja.CsvConverter.clients.*.fetchJsonsFromFirstService(..))")
////    public Object around(ProceedingJoinPoint jp) throws Throwable {
////        List<Double> cpus = new ArrayList<>();
////        // Create a CompletableFuture to run the method asynchronously
////        CompletableFuture<Object> future = CompletableFuture.supplyAsync(() -> {
////            try {
////                return timer.recordCallable(() -> {
////
////                    try {
////                        return jp.proceed();
////                    } catch (Throwable throwable) {
////                        throw new RuntimeException(throwable);
////                    }
////                });
////            } catch (Throwable throwable) {
////                throw new RuntimeException(throwable);
////            }
////        });
////        registry.get("system.cpu.usage").gauge().value();
////        // While loop to print "hi" until the future is done
////        while (!future.isDone()) {
////            cpus.add(OSBEAN.getCpuLoad());
////            // To avoid overwhelming the console and CPU, add a small sleep
////            try {
////                TimeUnit.MILLISECONDS.sleep(100);
////            } catch (InterruptedException e) {
////                Thread.currentThread().interrupt();
////                throw new RuntimeException(e);
////            }
////        }
////
////        // Get the result of the future (this will also propagate any exceptions)
////        try {
////
////            return future.get();
////        } catch (InterruptedException | ExecutionException e) {
////            throw new RuntimeException(e);
////        }
////    }
//
//
//    @Async
//    @Around(serviceMethods)
//    public Object around2(ProceedingJoinPoint jp) throws Throwable {
//
//        CompletableFuture<Object> future = CompletableFuture.supplyAsync(() -> {
//            try {
//                logger.info("hi im logger future");
//
//                return timerMethods.recordCallable(() -> {
//                    try {
//                        return jp.proceed();
//                    } catch (Throwable throwable) {
//                        throw new RuntimeException(throwable);
//                    }
//                });
//            } catch (Throwable throwable) {
//                throw new RuntimeException(throwable);
//            }
//        });
//
//        logger.info("hi im logger ");
////        // Monitor CPU load asynchronously and record it
////        CompletableFuture<Void> monitorFuture = CompletableFuture.runAsync(() -> {
////            takeMeasurement(future);
////        });
//
//        // Wait for the method execution and monitoring to complete
//        try {
//            return future.get();
//        } catch (InterruptedException | ExecutionException e) {
//            throw new RuntimeException(e);
//        }
//    }
//    @Around(serviceMethods)
//    public Object measureCpuUsage(ProceedingJoinPoint jp) throws Throwable {
//        // Uruchomienie zadania asynchronicznego
//        CompletableFuture<Object> future = CompletableFuture.supplyAsync(() -> {
//            System.out.println("1");
//            try {
//                System.out.println("2");
//                return jp.proceed();
//            } catch (Throwable e) {
//                throw new RuntimeException(e);
//            }
//        });
//
//        // Uruchomienie takeMeasurement równolegle
//        CompletableFuture<Void> measurementFuture = CompletableFuture.runAsync(() -> {
//            try {
//                takeMeasurement(future);
//            } catch (Throwable e) {
//                throw new RuntimeException(e);
//            }
//        });
//
//        // Czekanie na zakończenie obu zadań
//        CompletableFuture<Void> combinedFuture = CompletableFuture.allOf(future, measurementFuture);
//
//        // Czekanie na zakończenie obu zadań
//        combinedFuture.join();
//
//        // Zwrócenie wyniku głównego zadania
//        return future.get();
//    }
//
//    private MeasurementDTO takeMeasurement(CompletableFuture<Object> future) {
//        Instant start = Instant.now();
//        ArrayList<Double> cpuLoads = new ArrayList<>();
//        ArrayList<Double> memory = new ArrayList<>();
//
//
//        while (!future.isDone()) {
//            double cpuLoad = OSBEAN.getCpuLoad();
//            long freeMemorySize = OSBEAN.getFreeMemorySize();
//            cpuLoads.add(cpuLoad);
//            memory.add(((double) freeMemorySize / (1024 * 1024 * 1024)));
//            try {
//                TimeUnit.MILLISECONDS.sleep(500);
//            } catch (InterruptedException e) {
//                Thread.currentThread().interrupt();
//                throw new RuntimeException(e);
//            }
//        }
//        Instant stop = Instant.now();
//        MeasurementDTO dto = new MeasurementDTO(cpuLoads, memory, Duration.between(start, stop).toMillis());
//        System.out.println(dto);
//        return dto;







//    Gauge.builder("cpu.usage", OSBEAN, value -> OSBEAN.getCpuLoad())
//            .description("").baseUnit(BaseUnits.PERCENT).register(registry);
