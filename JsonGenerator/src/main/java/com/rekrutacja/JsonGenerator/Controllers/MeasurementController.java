package com.rekrutacja.JsonGenerator.Controllers;

import com.rekrutacja.JsonGenerator.DTOs.MeasurementDTO;
import com.rekrutacja.JsonGenerator.Services.Measurement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MeasurementController {
    private Measurement measurement;
    public MeasurementController(Measurement measurement) {
        this.measurement = measurement;
    }
    @GetMapping(value = "/measurements/{size}")
    public ResponseEntity<MeasurementDTO> getMeasurementsData(@PathVariable int size) {

        MeasurementDTO measure = measurement.takeMeasurement(size);
        return ResponseEntity.ok(measure);
    }
}
