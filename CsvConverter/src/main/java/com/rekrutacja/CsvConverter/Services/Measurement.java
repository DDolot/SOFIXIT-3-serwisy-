package com.rekrutacja.CsvConverter.Services;


import com.rekrutacja.CsvConverter.DTOs.MeasurementDTO;
import com.sun.management.OperatingSystemMXBean;
import org.springframework.stereotype.Component;

import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
public class Measurement {

    private CsvConverterService secondServiceDifferent;
    private final OperatingSystemMXBean osBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();

    public Measurement(CsvConverterService secondServiceDifferent) {
        this.secondServiceDifferent = secondServiceDifferent;
    }

    public MeasurementDTO takeMeasurement(CompletableFuture<Void> future){

        List<Double> cpuLoads = new ArrayList<>();
        List<Double> memory = new ArrayList<>();
        long startTime = System.currentTimeMillis();

        boolean measure = true;

        while (measure) {

            double cpuLoad = osBean.getProcessCpuLoad();
            long usedMemory = osBean.getTotalMemorySize() - osBean.getFreeMemorySize();

            cpuLoads.add(cpuLoad * 100);
            memory.add((double) usedMemory/1000000);

            if (future.isDone()) {
                measure = false;
            }
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        future.join();
        long stopTime = System.currentTimeMillis();
        long duration = (stopTime - startTime);

        return new MeasurementDTO(cpuLoads,memory,duration);
    }
}
