package com.rekrutacja.CsvConverter.Services;

import com.rekrutacja.CsvConverter.DTOs.MeasurementDTO;
import com.sun.management.OperatingSystemMXBean;
import io.micrometer.core.instrument.MeterRegistry;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.management.ManagementFactory;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@org.aspectj.lang.annotation.Aspect
@Component
    public class Aspect implements Observer{

    private final String serviceMethods = "execution(* com.rekrutacja.CsvConverter.Services.*.convertToCSV(..))";
        private Measurement measurement;
        private MeasurementDTO fetch;
        private MeasurementDTO calculate;
        private MeasurementDTO convert;
        private  Long time;
    private static final OperatingSystemMXBean OSBEAN = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();

    private static final long totalMemory = OSBEAN.getTotalMemorySize();

        public Aspect(Measurement measurement) {
            this.measurement = measurement;

        }

        @Around("execution(* com.rekrutacja.CsvConverter.clients.*.fetchJsonsFromFirstService(..))")
        public Object aroundFetchJsonsFromFirstService(ProceedingJoinPoint jp) throws Throwable {
            CompletableFuture<Object> futureProcessMethod = CompletableFuture
                    .supplyAsync(() -> {
                        try {
                            return jp.proceed();
                        } catch (Throwable e) {
                            throw new RuntimeException(e);
                        }
                    });

            fetch = takeMeasurement(futureProcessMethod);

            return futureProcessMethod.get();
        }



    @Around("execution(* com.rekrutacja.CsvConverter.Services.CsvConverterService.calculate(..)))")
    public Object aroundCalculate(ProceedingJoinPoint jp) throws Throwable {
            CompletableFuture<Object> futureProcessMethod = CompletableFuture
                    .supplyAsync(() -> {
                        try {
                            return jp.proceed();
                        } catch (Throwable e) {
                            throw new RuntimeException(e);
                        }
                    });


        calculate = takeMeasurement(futureProcessMethod);


        return futureProcessMethod.get();
        }
        @Around("execution(* com.rekrutacja.CsvConverter.Services.CsvConverterService.convertToCSV(..)))")
        public Object aroundConvertToCsv(ProceedingJoinPoint jp) throws Throwable {
            CompletableFuture<Object> futureProcessMethod = CompletableFuture
                    .supplyAsync(() -> {
                        try {
                            return jp.proceed();
                        } catch (Throwable e) {
                            throw new RuntimeException(e);
                        }
                    });


        convert = takeMeasurement(futureProcessMethod);


        return futureProcessMethod.get();
        }


    @Override
    public MeasurementDTO getFetch() {
        return fetch;
    }

    @Override
    public MeasurementDTO getCalculate() {
        return calculate;
    }

    @Override
    public MeasurementDTO getConvert() {
        return convert;
    }
    @Override
    public MeasurementDTO takeMeasurement(CompletableFuture<Object> future){
        ArrayList<Double> cpuLoads = new ArrayList<>();
        ArrayList<Double> memory = new ArrayList<>();
        Instant start = Instant.now();

        while (!future.isDone()) {

            double cpuLoad = OSBEAN.getSystemCpuLoad();

            long usedMemory = totalMemory - OSBEAN.getFreeMemorySize();
            // wez registy memory bo to jest choojnia jakaś
            cpuLoads.add(cpuLoad * 100);
            memory.add((double) usedMemory/(1024*1024));

            try {
                Thread.sleep(250);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        Instant stop = Instant.now();
        time = Duration.between(start, stop).toMillis();
        MeasurementDTO measurementDTO = new MeasurementDTO();
        measurementDTO.setProcessCpuLoad(cpuLoads);
        measurementDTO.setUsedMemorySize(memory);
        measurementDTO.setTime(time);



        return measurementDTO;
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
