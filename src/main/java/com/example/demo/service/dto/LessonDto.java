package com.example.demo.service.dto;


import com.example.demo.model.LessonStatus;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonSerialize
public class LessonDto {

    private Integer id;
//    private LocalTime startTime;
//    private LocalTime endTime;
    private String startTime;
    private String endTime;
    private String place;
    private LocalDate date;
    private LessonStatus lessonStatus;
    private Integer vehicleId;
    private Integer instructorId;
    private Integer studentId;
}
