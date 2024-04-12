package com.rekrutacja.FirstService.Services;


import com.sun.management.OperatingSystemMXBean;
import lombok.Data;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.lang.management.ManagementFactory;

@Data
@Component
public class PerformanceMonitor implements Runnable{

        private final OperatingSystemMXBean os = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();

        private double processCpuLoad;
        private long totalMemorySize;
        private long freeMemorySize;

        @Override
        public void run() {
            this.processCpuLoad = os.getProcessCpuLoad();
            this.totalMemorySize = os.getTotalMemorySize();
            this.freeMemorySize = os.getFreeMemorySize();
        }
        // daj to w każdej metodzie w 2 serwisie  bo każdy call to osobny jvm process


}
