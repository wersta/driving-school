package com.example.demo.service.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonSerialize
public class RatingDto {

    private Integer id;

    private String description;
    private int starsNumber;

   private Integer instructorId;
}
