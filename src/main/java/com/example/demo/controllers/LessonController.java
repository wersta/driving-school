package com.example.demo.controllers;

import com.example.demo.service.LessonService;
import com.example.demo.service.dto.LessonDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("http://localhost:8080")
@RequestMapping("/lesson")
@ResponseBody
public class LessonController {

    @Autowired
    private LessonService lessonService;

    @GetMapping("/{lessonId}")
    public LessonDto getLesson(@PathVariable Integer lessonId) {
        return lessonService.findLesson(lessonId);
    }

    @PostMapping("/{studentId}")
    public ResponseEntity<?> createLesson(@RequestBody LessonDto lessonDto,@PathVariable Integer studentId) {
        LessonDto newLesson=lessonService.createLesson(lessonDto, studentId);
        if (newLesson != null) {
            return new ResponseEntity<>(newLesson, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
        }
    }
//    @PutMapping("/accept/{lessonId}")
//    public ResponseEntity<?> acceptLesson(@PathVariable Integer lessonId) {
//        LessonDto lesson=lessonService.acceptLesson(lessonId);
//        if (lesson != null) {
//            return new ResponseEntity<>(lesson, HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
//        }
//    }
//    @PutMapping("/deny/{lessonId}")
//    public ResponseEntity<?> denyLesson(@PathVariable Integer lessonId) {
//        LessonDto lesson=lessonService.denyLesson(lessonId);
//        if (lesson != null) {
//            return new ResponseEntity<>(lesson, HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
//        }
//    }
}
