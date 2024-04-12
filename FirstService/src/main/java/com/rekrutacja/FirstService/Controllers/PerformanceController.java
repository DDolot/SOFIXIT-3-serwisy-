package com.rekrutacja.FirstService.Controllers;

import com.rekrutacja.FirstService.Models.DTO;
import com.rekrutacja.FirstService.Services.MethodInterceptor;
import com.rekrutacja.FirstService.Services.PerformanceMonitor;
import com.rekrutacja.FirstService.Services.FirstService;
import com.rekrutacja.FirstService.Services.ProcessMonitor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@Controller
public class PerformanceController {
    private PerformanceMonitor performanceMonitor;
    private FirstService firstService;

    private ProcessMonitor processMonitor;


    public PerformanceController(PerformanceMonitor performanceMonitor, FirstService firstService, ProcessMonitor processMonitor, MethodInterceptor methodInterceptor) {
        this.performanceMonitor = performanceMonitor;
        this.firstService = firstService;
        this.processMonitor = processMonitor;
        this.methodInterceptor = methodInterceptor;
    }

    private final MethodInterceptor methodInterceptor;



    @GetMapping("/generateJsonList/{size}")
    public void getGenerateJsonListData(
            @PathVariable int size
    ) {
        methodInterceptor.generateJsonListExecution(size);
    }

    @GetMapping("/perf/{size}")
    public void getGenerateJsonListDataa(
            @PathVariable int size
    ) {
        processMonitor.startMonitoring();

        // Simulate a long-running task on another thread
        Thread taskThread = new Thread(() -> {
            try {
                firstService.generateJsonList(size);
                TimeUnit.SECONDS.wait(10);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        taskThread.start();
    }
    }
}
