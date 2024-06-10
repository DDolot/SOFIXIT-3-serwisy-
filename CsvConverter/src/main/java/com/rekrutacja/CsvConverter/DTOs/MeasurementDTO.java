package com.rekrutacja.CsvConverter.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.StringJoiner;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class MeasurementDTO {
    private List<String> processCpuLoad;
    private List<String> usedMemorySize;
    private long time;
}




