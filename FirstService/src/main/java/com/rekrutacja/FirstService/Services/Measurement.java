package com.rekrutacja.FirstService.Services;
import java.lang.management.ManagementFactory;

import com.rekrutacja.FirstService.Models.MeasurementDTO;
import com.sun.management.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
public class Measurement {
    @Autowired
    private FirstService firstService;
    private final OperatingSystemMXBean osBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();

    public Measurement(FirstService firstService) {
        this.firstService = firstService;
    }

    public MeasurementDTO takeMeasurement(int size) {
        List<Double> cpuLoads = new ArrayList<>();
        List<Double> memory = new ArrayList<>();
        long startTime = System.currentTimeMillis();
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {

            firstService.generateJsonList(size);

        });

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
