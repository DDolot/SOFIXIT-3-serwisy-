package com.rekrutacja.FirstService.Controllers;

import com.rekrutacja.FirstService.Models.MeasurementDTO;
import com.rekrutacja.FirstService.Services.FirstService;
import com.rekrutacja.FirstService.Services.Measurement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MeasurementController {

    private Measurement measurement;



    public MeasurementController(Measurement complatableFutureMeasure) {
        this.measurement = complatableFutureMeasure;

    }

    @GetMapping(value = "/measurements/{size}")
    public ResponseEntity<MeasurementDTO> getMeasurementsData(@PathVariable int size) {

        MeasurementDTO measure = measurement.takeMeasurement(size);

        return ResponseEntity.ok(measure);
    }
}
