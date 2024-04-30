package com.rekrutacja.JsonGenerator.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MeasurementDTO {
    private List<Double> processCpuLoad;
    private List<Double> usedMemorySize;
}




