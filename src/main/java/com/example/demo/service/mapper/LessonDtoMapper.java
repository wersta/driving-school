package com.example.demo.service.mapper;

import com.example.demo.model.Lesson;
import com.example.demo.service.dto.LessonDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class LessonDtoMapper {

    @Autowired
    private VehicleDtoMapper mapper;

    public LessonDto toDto(Lesson lesson) {

        LocalTime time=lesson.getStartTime();
        LocalTime endTime=lesson.getEndTime();

        return new LessonDto(lesson.getId(),lesson.getStartTime().toString(), lesson.getEndTime().toString(), lesson.getPlace(),lesson.getDate(),
                lesson.getLessonStatus(),lesson.getVehicle().getId(),lesson.getInstructor().getId(),lesson.getStudent().getId());
    }

    public List<LessonDto> toDtoLessonList(List<Lesson>lessonList )
    {
        List<LessonDto> lessonListDto = new ArrayList<>();
        lessonList.forEach(lesson -> lessonListDto.add(this.toDto(lesson)));
        return lessonListDto;
    }
}
