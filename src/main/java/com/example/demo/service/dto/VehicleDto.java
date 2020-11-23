package com.example.demo.service.dto;

import com.example.demo.model.VehicleType;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonSerialize
public class VehicleDto {
    @JsonProperty
    private Integer id;
    @JsonProperty
    private String brand;
    @JsonProperty
    private String model;
    @JsonProperty
    private VehicleType vehicleType;
}
