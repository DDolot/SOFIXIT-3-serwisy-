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


    private final OperatingSystemMXBean OSBEAN = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
    public MeasurementDTO takeMeasurement(CompletableFuture<Void> future){

        List<Double> cpuLoads = new ArrayList<>();
        List<Double> memory = new ArrayList<>();

        boolean measure = true;

        while (measure) {

            double cpuLoad = OSBEAN.getProcessCpuLoad();
            long usedMemory = OSBEAN.getTotalMemorySize() - OSBEAN.getFreeMemorySize();

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


        return new MeasurementDTO(cpuLoads,memory,0);
    }
}
