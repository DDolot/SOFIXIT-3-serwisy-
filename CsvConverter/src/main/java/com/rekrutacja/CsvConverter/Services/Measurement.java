package com.rekrutacja.CsvConverter.Services;


import com.rekrutacja.CsvConverter.DTOs.MeasurementDTO;
import com.sun.management.OperatingSystemMXBean;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.search.RequiredSearch;
import lombok.Data;
import org.springframework.stereotype.Component;

import javax.management.MBeanInfo;
import java.lang.management.ManagementFactory;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Component
@Data
public class Measurement {
    private  List<Double> cpuLoads = new ArrayList<>();
    private  List<Double> memory = new ArrayList<>();
    private  Long time;
    private static final OperatingSystemMXBean OSBEAN = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();

    private static final long totalMemory = OSBEAN.getTotalMemorySize();



    protected Map<String,MeasurementDTO> takeMeasurement(String methodName,CompletableFuture<Object> future){
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
        Map<String,MeasurementDTO> map = new HashMap<>();
        map.put(methodName,measurementDTO);
        return map;
    }
}
