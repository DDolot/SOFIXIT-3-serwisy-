package com.rekrutacja.FirstService.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MeasurementDTO {
    private List<Double> processCpuLoad;
    private List<Double> usedMemorySize;
    private long time;


}




