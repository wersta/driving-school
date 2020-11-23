package com.example.demo.service;

import com.example.demo.model.*;
import com.example.demo.repository.InstructorRepository;
import com.example.demo.service.dto.LessonDto;
import com.example.demo.service.dto.RatingDto;
import com.example.demo.service.dto.UserDto;
import com.example.demo.service.dto.UserSecondDto;
import com.example.demo.service.mapper.InstructorDtoMapper;
import com.example.demo.service.mapper.LessonDtoMapper;
import com.example.demo.service.mapper.RatingDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class InstructorService {

    @Autowired
    private InstructorRepository instructorRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private RatingDtoMapper ratingMapper;

    @Autowired
    private InstructorDtoMapper instructorMapper;

    @Autowired
    private LessonDtoMapper lessonMapper;

    @Autowired
    private LessonService lessonService;

    @Autowired
    private NotificationService notificationService;

    public Instructor findById(Integer instructorId) {
        return instructorRepository.findById(instructorId).isPresent() ? instructorRepository.findById(instructorId).get() : null;
    }

    public Instructor createInstructor(UserDto userDto) {
        userDto.setUserRole(UserRole.INSTRUCTOR);
        User user = userService.createUser(userDto);
        Instructor instructor = new Instructor(null, user, Collections.emptyList(), Collections.emptyList(), Collections.emptyList());
        instructorRepository.save(instructor);
        return instructor;
    }

    public boolean deleteInstructor(Integer instructorId) {
        Instructor student = findById(instructorId);

        if (student != null) {
            instructorRepository.delete(student);
            return true;
        }
        return false;
    }

    public List<RatingDto> ratingList(Integer instructorId) {
        Instructor instructor = findById(instructorId);
        if (instructor != null) {
            List<Rating> ratingList = new ArrayList<>();
            ratingList = instructor.getRatingList();
            return ratingMapper.toDtoRatingList(ratingList);
        } else return null;
    }

    public List<LessonDto> lessonList(Integer instructorId) {
        Instructor instructor = findById(instructorId);
        if (instructor != null) {
            List<Lesson> lessonList = new ArrayList<>();
            lessonList = instructor.getLessonList();
            return lessonMapper.toDtoLessonList(lessonList);
        } else return null;
    }

    public List<UserSecondDto> instructorToList() {
        List<Instructor> instructorList = instructorRepository.findAll();
        List<UserSecondDto> dtoList = new ArrayList<>();
        for (Instructor e : instructorList) {
            dtoList.add(instructorMapper.toDto(e));
        }
        return dtoList;
    }

    public UserSecondDto findInstructor(Integer instructorId) {
        Instructor instructor = findById(instructorId);
        if (instructor != null) {
            return instructorMapper.toDto(instructor);
        }
        return null;
    }

    public void addNotification(Instructor instructor, Notification notification) {
        instructor.getNotificationList().add(notification);
        instructorRepository.save(instructor);
    }

    public void check(Integer instructorId) {
        Instructor instructor = findById(instructorId);
        if (instructor != null) {
            List<Lesson> lessonList = lessonService.findByInstructor(instructor);
            LocalDate date = LocalDate.now().plusDays(1);

            for (Lesson e : lessonList) {
                if (e.getDate().equals(date)) {
                    String description = "Czas rozpoczęcia: " + e.getStartTime() + ". Miejsce rozpoczęcia: " + e.getPlace();
                    Notification notification = new Notification(null, "Powiadomienie o zajęciach!", description, false);
                    notificationService.save(notification);
                    addNotification(instructor, notification);
                }
            }
        }
    }
    public List<Notification> findAllNotifications(Integer instructorId) {

        Instructor instructor = findById(instructorId);
        if(instructor!=null) {
            return instructor.getNotificationList();
        }else{
            return null;}
    }

    public List<Notification> findAllUnread(Integer instructorId) {
        Instructor instructor = findById(instructorId);
        if(instructor != null) {
            List<Notification> notificationList = new ArrayList<>();
            for (Notification e : instructor.getNotificationList()) {
                if (!e.isNotificationStatus()) notificationList.add(e);
            }
            return notificationList;
        }else{
            return null; }
    }
}
