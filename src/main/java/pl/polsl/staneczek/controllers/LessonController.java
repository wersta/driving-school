package pl.polsl.staneczek.controllers;

import pl.polsl.staneczek.service.dto.LessonDto;
import pl.polsl.staneczek.service.LessonService;
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

}
