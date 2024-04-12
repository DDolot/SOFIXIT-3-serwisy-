package com.rekrutacja.FirstService.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DTO {
    private double processCpuLoad;
    private long totalMemorySize;
    private long freeMemorySize;
    private long usedMemory;


}
