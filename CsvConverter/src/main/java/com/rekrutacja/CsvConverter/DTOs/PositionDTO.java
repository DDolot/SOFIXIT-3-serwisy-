package com.rekrutacja.CsvConverter.DTOs;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonPropertyOrder
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PositionDTO {

    private String _type;

    private long id;

    private Boolean key;

    private String name;

    private String fullName;

    private String airportCode;

    private String type;

    private String country;

    private GeoPositionDTO geoPosition;

    private long locationId;


    private boolean inEurope;

    private String countryCode;

    private boolean coreCountry;

    private Boolean distance;

    public float getLatitude() {
        return geoPosition.latitude;
    }
    public float getLongitude() {
        return geoPosition.longitude;
    }

    public void setGeoPosition(GeoPositionDTO geoPosition) {
        this.geoPosition = geoPosition;
    }



}










