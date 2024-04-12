package com.rekrutacja.FirstService.Services;

import com.sun.management.OperatingSystemMXBean;

import java.lang.management.ManagementFactory;
import java.util.concurrent.TimeUnit;

public class ProcessMonitor {


        private final OperatingSystemMXBean osBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();

        public void startMonitoring() {
            Thread monitorThread = new Thread(() -> {
                while (!Thread.currentThread().isInterrupted()) {
                    try {

                        measureCPUUsage();
                        measureMemoryUsage();

                        // Sleep for a certain interval
                        TimeUnit.SECONDS.wait(1); // Adjust interval as needed
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            });
            monitorThread.start();
        }

        private void measureCPUUsage() {
            double cpuLoad = osBean.getProcessCpuLoad() * 100;
            System.out.println("CPU Load: " + cpuLoad);
        }

        private void measureMemoryUsage() {
            long freeMemoryGB = osBean.getFreeMemorySize() / (1024 * 1024 * 1024); // Convert bytes to GB
            System.out.println("Free Memory (GB): " + freeMemoryGB);
        }


    }

