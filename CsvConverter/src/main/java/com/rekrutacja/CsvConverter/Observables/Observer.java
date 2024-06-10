package com.rekrutacja.CsvConverter.Observables;

import com.rekrutacja.CsvConverter.DTOs.MeasurementDTO;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface Observer {
    MeasurementDTO getFetch();
    MeasurementDTO getCalculate();
    MeasurementDTO getConvert();

    MeasurementDTO takeMeasurement(CompletableFuture<Object> future);


}
