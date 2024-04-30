package com.rekrutacja.CsvConverter.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.StringJoiner;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MeasurementDTO {
    private List<Double> processCpuLoad;
    private List<Double> usedMemorySize;
    private long time;


}




