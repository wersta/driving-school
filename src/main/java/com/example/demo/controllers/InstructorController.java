package com.example.demo.controllers;


import com.example.demo.service.InstructorService;
import com.example.demo.service.dto.UserDto;
import com.example.demo.service.dto.UserSecondDto;
import com.example.demo.model.Notification;
import com.example.demo.service.dto.LessonDto;
import com.example.demo.service.dto.RatingDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("instructor")
@ResponseBody
public class InstructorController {

    @Autowired
    private InstructorService instructorService;


    @GetMapping("/all")
    public List<UserSecondDto> getInstructors() {
        return instructorService.instructorToList();
    }

    @GetMapping("/{instructorId}")
    public UserSecondDto getInstructor(@PathVariable Integer instructorId) {
        return instructorService.findInstructor(instructorId);
    }

    @GetMapping("/lesson/{instructorId}")
    public List<LessonDto> getLessonsFromSpecificInstructor(@PathVariable Integer instructorId) {
        return instructorService.lessonList(instructorId);
    }
    @GetMapping("/ratings/{instructorId}")
    public List<RatingDto> getRatingsFromSpecificInstructor(@PathVariable Integer instructorId) {
        return instructorService.ratingList(instructorId);
    }
    @GetMapping("/notification/{instructorId}")
    public List<Notification> getAllNotifications(@PathVariable Integer instructorId) {
        return instructorService.findAllNotifications(instructorId);
    }

    @GetMapping("/notification/unread/{instructorId}")
    public List<Notification> getUnreadNotifications(@PathVariable Integer instructorId) {
        return instructorService.findAllUnread(instructorId);
    }

    @PostMapping
    public ResponseEntity<HttpStatus> createInstructor(@RequestBody UserDto userDto) {
        if (instructorService.createInstructor(userDto) != null) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
        }
    }

    @DeleteMapping("/{instructorId}")
    public ResponseEntity<HttpStatus> deleteInstructor(@PathVariable Integer instructorId) {
        if (instructorService.deleteInstructor(instructorId)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
        }
    }
}
