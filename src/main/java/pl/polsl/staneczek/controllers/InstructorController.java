package pl.polsl.staneczek.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.polsl.staneczek.model.Instructor;
import pl.polsl.staneczek.model.Notification;
import pl.polsl.staneczek.service.InstructorService;
import pl.polsl.staneczek.service.dto.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("instructor")
@ResponseBody
@CrossOrigin(origins = "http://localhost:8080")
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
    public List<LessonDto> getLessons(@PathVariable Integer instructorId) {
        return instructorService.lessonList(instructorId);
    }
//       @GetMapping("/lesson/accepted/{instructorId}")
//    public List<LessonDto> getAcceptedLessons(@PathVariable Integer instructorId) {
//        return instructorService.acceptedLessonList(instructorId);
//    }
    @GetMapping("/lesson/waiting/{instructorId}")
    public List<LessonDto> getWaitingLessons(@PathVariable Integer instructorId) {
        return instructorService.waitingLessonList(instructorId);
    }


    @PatchMapping("lesson/accept/{instructorId}")
    public ResponseEntity<?> acceptLesson(@PathVariable Integer instructorId, @RequestBody LessonDto dto) {

        if (instructorService.acceptLesson(instructorId, dto)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
        }
    }

    @PatchMapping("lesson/deny/{instructorId}")
    public ResponseEntity<?> denyLesson(@PathVariable Integer instructorId, @RequestBody LessonDto dto) {

        if (instructorService.denyLesson(instructorId, dto)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
        }
    }

    @PostMapping("/freeHours")
    public ResponseEntity<?> freeHours(@RequestBody DateDto dto) {
        ArrayList<ArrayList<Integer>> list=instructorService.freeHours(dto);
        if (list!=null) {
            return new ResponseEntity<>(list, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
        }

    }
    @GetMapping("/ratings/{instructorId}")
    public List<RatingDto> getRatingsFromSpecificInstructor(@PathVariable Integer instructorId) {
        return instructorService.ratingList(instructorId);
    }

    @GetMapping("/averageRating/{instructorId}")
    public float getAverageRating(@PathVariable Integer instructorId) {
        return instructorService.averageRating(instructorId);
    }
//    @GetMapping("/shwoRatings/{email}")
//    public List<RatingDto> getRatingsFromInstructor(@PathVariable String email) {
//        return instructorService.findInstructorRatingsByEmail(email);
//    }
    @PatchMapping("/{instructorId}")
    public ResponseEntity<?> updateInstructor(@PathVariable Integer instructorId, @RequestBody UserSecondDto dto) {

        Instructor updatedVehicle = instructorService.updateInstructor(instructorId, dto);

        if (updatedVehicle != null) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
        }
    }

    @GetMapping("/notification/{instructorId}")
    public List<Notification> getUnreadNotifications(@PathVariable Integer instructorId) {
        return instructorService.findAllUnread(instructorId);
    }
    @PatchMapping("notification/read/{instructorId}")
    public ResponseEntity<?> readNotification(@PathVariable Integer instructorId, @RequestBody NotificationDto dto) {

        if (instructorService.read(instructorId, dto)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
        }
    }

    @PostMapping("/add")
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
    @PatchMapping("changePassword/{instructorId}")
    public ResponseEntity<?> changePassword(@PathVariable Integer instructorId, @RequestBody UserDto dto) {
        if (instructorService.changePassword(instructorId, dto)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
        }
    }
}
