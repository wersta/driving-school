package com.example.demo.service;

import com.example.demo.model.*;
import com.example.demo.repository.LessonRepository;
import com.example.demo.service.dto.LessonDto;
import com.example.demo.service.mapper.LessonDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LessonService {

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private LessonDtoMapper lessonMapper;

    @Autowired
    private InstructorService instructorService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private VehicleService vehicleService;

    public Lesson findById(Integer lessonId) {
        return lessonRepository.findById(lessonId).isPresent() ? lessonRepository.findById(lessonId).get() : null;
    }

    public LessonDto findLesson(Integer ratingId)
    {
        Lesson lesson=findById(ratingId);
        if(lesson != null) {
            return lessonMapper.toDto(lesson);
        }
        return null;
    }

    public LessonDto createLesson(LessonDto lessonDto) {
        Instructor instructor = instructorService.findById(lessonDto.getInstructorId());
        Student student = studentService.findById(lessonDto.getStudentId());
        Vehicle vehicle = vehicleService.findById(lessonDto.getVehicleId());

        Lesson lesson = new Lesson();
        lesson.setId(lessonDto.getId());
        lesson.setEndTime(lessonDto.getEndTime());
        lesson.setStartTime(lessonDto.getStartTime());
        lesson.setPlace(lessonDto.getPlace());
        lesson.setLessonStatus(LessonStatus.WAITING);
        lesson.setVehicle(vehicle);
        lesson.setInstructor(instructor);
        lesson.setStudent(student);

        lessonRepository.save(lesson);
        return lessonMapper.toDto(lesson);
    }

    public List<Lesson> findByInstructor(Instructor instructor) {
        return lessonRepository.findAllByInstructor(instructor);
    }

    public List<Lesson> findByStudent(Student student) {
        return lessonRepository.findAllByStudent(student);
    }

    public LessonDto acceptLesson(Integer lessonId) {
        Lesson updatedLesson=findById(lessonId);
        if(updatedLesson!=null) {
            updatedLesson.setLessonStatus(LessonStatus.ACCEPTED);
            lessonRepository.save(updatedLesson);
            return lessonMapper.toDto(updatedLesson);
        }else
            {return null;}

    }
    public LessonDto denyLesson(Integer lessonId) {
        Lesson updatedLesson=findById(lessonId);
        if(updatedLesson!=null) {
            updatedLesson.setLessonStatus(LessonStatus.DENIED);
            lessonRepository.save(updatedLesson);
            return lessonMapper.toDto(updatedLesson);
        }else {return null;}

    }
}
