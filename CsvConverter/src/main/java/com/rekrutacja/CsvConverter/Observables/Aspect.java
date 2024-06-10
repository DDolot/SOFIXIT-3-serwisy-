package com.rekrutacja.CsvConverter.Observables;

import com.rekrutacja.CsvConverter.DTOs.MeasurementDTO;
import com.sun.management.OperatingSystemMXBean;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.springframework.stereotype.Component;

import java.lang.management.ManagementFactory;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

@org.aspectj.lang.annotation.Aspect
@Component
    public class Aspect implements Observer {

        private final String serviceMethods = "execution(* com.rekrutacja.CsvConverter.Services.*.convertToCSV(..))";

        private MeasurementDTO fetch;
        private MeasurementDTO calculate;
        private MeasurementDTO convert;

        private static final OperatingSystemMXBean OSBEAN = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();

        private static final long totalMemory = OSBEAN.getTotalMemorySize();



        @Around("execution(* com.rekrutacja.CsvConverter.clients.*.fetchJsonsFromFirstService(..))")
        private Object aroundFetchJsonsFromFirstService(ProceedingJoinPoint jp) throws Throwable {
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
    private Object aroundCalculate(ProceedingJoinPoint jp) throws Throwable {
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
        ArrayList<String> cpuLoads = new ArrayList<>();
        ArrayList<String> memory = new ArrayList<>();
        Long time;
        Instant start = Instant.now();

        while (!future.isDone()) {

            double cpuLoad = OSBEAN.getSystemCpuLoad();

            long usedMemory = totalMemory - OSBEAN.getFreeMemorySize();
            cpuLoads.add(String.format("%.2f%%", cpuLoad));
            memory.add(String.format("%.2f MB ",(double) usedMemory/(1024*1024)));


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

