package pl.polsl.staneczek.service.dto;

import pl.polsl.staneczek.model.CourseType;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonSerialize
public class CourseDto {
    @JsonProperty
    private Integer id;
    @JsonProperty
    private String name;
    @JsonProperty
    private double price;
    @JsonProperty
    private LocalDate startDate;
    @JsonProperty
    private CourseType courseType;
    @JsonProperty
    private int hours;
}
