package com.rekrutacja.JsonGenerator.Services;

import com.sun.management.OperatingSystemMXBean;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.management.ManagementFactory;

@SpringBootTest
class FirstServiceTest {

    private JsonGeneratorService firstService;

    @BeforeEach
    public void setUp() {
        firstService = new JsonGeneratorService();
    }


        public String generatePerformanceReport() {
            // Get the OperatingSystemMXBean instance
            OperatingSystemMXBean osBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();

            // Get the start time
            long startTime = System.currentTimeMillis();

            // Perform the CPU-intensive operation
            firstService.generateJsonList(1000);

            // Get the end time
            long endTime = System.currentTimeMillis();

            // Calculate the elapsed time
            long elapsedTime = endTime - startTime;

            // Measure CPU load during the execution of the method
            double cpuLoadDuringExecution = getAverageCpuLoadDuringExecution(startTime, endTime, osBean);

            // Generate performance report
            return String.format("CPU load during execution: %.2f%%, Elapsed time: %d ms", cpuLoadDuringExecution/1000000000, elapsedTime);
        }

        private double getAverageCpuLoadDuringExecution(long startTime, long endTime, OperatingSystemMXBean osBean) {
            long currentTime = startTime;
            double totalCpuLoad = 0;
            int numSamples = 0;

            // Sample CPU load at regular intervals during the execution of the method
            while (currentTime <= endTime) {
                double cpuLoad = osBean.getFreeMemorySize();
                totalCpuLoad += cpuLoad;
                numSamples++;
                try {
                    // Adjust sleep duration based on your requirement
                    Thread.sleep(1); // Sleep for 100 milliseconds
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                currentTime = System.currentTimeMillis();
            }

            // Calculate the average CPU load
            return totalCpuLoad / numSamples;
        }
    @Test
    void TT(){
        Measurement complatableFutureMeasure = new Measurement(firstService);

    }

    }

