package com.rekrutacja.SecondService.Models;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@JsonPropertyOrder
@AllArgsConstructor
@NoArgsConstructor
public class JsonModel {

    private String _type;

    private Integer id;

    private Boolean key;

    private String name;

    private String fullName;

    private String airportCode;

    private String type;

    private String country;

//    private Map<String, Double> geo_position;

    private GeoPosition geoPosition;

    private Integer locationId;


    private Boolean inEurope;

    private String countryCode;

    private Boolean coreCountry;

    private String distance;

    public Double getLatitude() {
        return geoPosition.latitude;
    }
    public Double getLongitude() {
        return geoPosition.longitude;
    }

    public void setGeoPosition(GeoPosition geoPosition) {
        this.geoPosition = geoPosition;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class GeoPosition {

        private Double latitude;

        private Double longitude;
    }

}










