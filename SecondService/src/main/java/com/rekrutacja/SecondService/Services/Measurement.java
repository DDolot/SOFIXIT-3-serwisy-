package com.rekrutacja.SecondService.Services;


import com.rekrutacja.SecondService.dtos.MeasurementDTO;
import com.rekrutacja.SecondService.dtos.PositionDTO;
import com.sun.management.OperatingSystemMXBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.management.ManagementFactory;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

@Component
public class Measurement {

    private SecondServiceDifferent secondServiceDifferent;
    private final OperatingSystemMXBean osBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();

    public Measurement(SecondServiceDifferent secondServiceDifferent) {
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
