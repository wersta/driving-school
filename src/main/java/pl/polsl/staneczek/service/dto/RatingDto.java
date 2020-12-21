package pl.polsl.staneczek.service.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonSerialize
public class RatingDto {

    private Integer id;
    private LocalDate date;
    private String description;
    private int starsNumber;

   private Integer instructorId;
}
