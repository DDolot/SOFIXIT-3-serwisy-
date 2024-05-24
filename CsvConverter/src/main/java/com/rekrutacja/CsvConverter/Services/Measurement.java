package com.rekrutacja.CsvConverter.Services;


import com.rekrutacja.CsvConverter.DTOs.MeasurementDTO;
import com.sun.management.OperatingSystemMXBean;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.search.RequiredSearch;
import org.springframework.stereotype.Component;

import javax.management.MBeanInfo;
import java.lang.management.ManagementFactory;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
public class Measurement {
    private List<Double> cpuLoads = new ArrayList<>();
    private List<Double> memory = new ArrayList<>();
    private static final OperatingSystemMXBean OSBEAN = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
    private MeterRegistry registry;
    private static final long totalMemory = OSBEAN.getTotalMemorySize();

    public Measurement(MeterRegistry registry) {
        this.registry = registry;
    }

    public MeasurementDTO takeMeasurement(CompletableFuture<Object> future){
        Instant start = Instant.now();
        while (!future.isDone()) {

            double cpuLoad = OSBEAN.getSystemCpuLoad();

            long usedMemory = totalMemory - OSBEAN.getFreeMemorySize();
            // wez registy memory bo to jest choojnia jakaś
            cpuLoads.add(cpuLoad * 100);
            memory.add((double) usedMemory/(1024*1024));
            RequiredSearch requiredSearch = registry.get("jvm.memory.used");
            System.out.println(requiredSearch.gauge().value());
            try {
                Thread.sleep(250);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        Instant stop = Instant.now();
        return new MeasurementDTO(cpuLoads,memory, Duration.between(start,stop).toMillis());
    }
}
