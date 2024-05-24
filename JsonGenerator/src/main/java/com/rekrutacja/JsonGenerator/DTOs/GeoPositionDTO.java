package com.rekrutacja.JsonGenerator.DTOs;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GeoPositionDTO {

        @JsonProperty("latitude")
        private double latitude;
        @JsonProperty("longitude")
        private double longitude;

}
