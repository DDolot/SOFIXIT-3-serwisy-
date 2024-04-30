package com.rekrutacja.JsonGenerator.Services;
import java.lang.management.ManagementFactory;

import com.rekrutacja.JsonGenerator.DTOs.MeasurementDTO;
import com.sun.management.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
public class Measurement {
    @Autowired
    private JsonGeneratorService jsonGeneratorService;
    private final OperatingSystemMXBean OSBEAN = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();

    public Measurement(JsonGeneratorService jsonGeneratorService) {
        this.jsonGeneratorService = jsonGeneratorService;
    }

    public MeasurementDTO takeMeasurement(int size) {
        List<Double> cpuLoads = new ArrayList<>();
        List<Double> memory = new ArrayList<>();
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            jsonGeneratorService.generateJsonList(size);
        });

        while (!future.isDone()) {
            double cpuLoad = OSBEAN.getProcessCpuLoad();
            long usedMemory = OSBEAN.getTotalMemorySize() - OSBEAN.getFreeMemorySize();

            cpuLoads.add(cpuLoad * 100);
            memory.add((double) usedMemory / (1024 * 1024));

        }

        return new MeasurementDTO(cpuLoads,memory);
    }
}
