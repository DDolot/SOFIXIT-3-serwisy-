package com.rekrutacja.CsvConverter.DTOs;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GeoPositionDTO {

        @JsonProperty("latitude")
        protected float latitude;
        @JsonProperty("longitude")
        protected float longitude;

}
