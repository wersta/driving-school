package pl.polsl.staneczek.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.polsl.staneczek.model.Notification;
import pl.polsl.staneczek.model.Student;
import pl.polsl.staneczek.service.StudentService;
import pl.polsl.staneczek.service.dto.*;

import java.util.List;


@RestController
@RequestMapping("/student")
@ResponseBody
@CrossOrigin(origins="http://localhost:8080", allowedHeaders = "*")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping("/all")
    public List<UserSecondDto> getStudents() {
        return studentService.studentToList();
    }

    @GetMapping("/{id}")
    public UserSecondDto getStudent(@PathVariable Integer id) {
        return studentService.findStudent(id);
    }


    @GetMapping("/course/{studentId}")
    public List<CourseDto> getCoursesFromSpecificStudent(@PathVariable Integer studentId) {
        return studentService.courseList(studentId);
    }

    @GetMapping("/lesson/{studentId}")
    public List<LessonDto> getLessonsFromSpecificStudent(@PathVariable Integer studentId) {
        return studentService.lessonList(studentId);
    }

    @GetMapping("/notification/{studentId}")
    public List<Notification> getUnreadNotifications(@PathVariable Integer studentId) {
        return studentService.findAllUnread(studentId);
    }
    @PatchMapping("notification/read/{studentId}")
    public ResponseEntity<?> readNotification(@PathVariable Integer studentId, @RequestBody NotificationDto dto) {

        if (studentService.read(studentId, dto)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> createStudent(@RequestBody UserDto userDto) {
        LoginDto loginDto=studentService.createStudent(userDto);
        if (loginDto != null) {
            return new ResponseEntity<>(loginDto,HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
        }
    }

    @DeleteMapping("/{studentId}")
    public ResponseEntity<HttpStatus> deleteStudent(@PathVariable Integer studentId) {
        if (studentService.deleteStudent(studentId)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
        }
    }

    @PatchMapping("/{studentId}")
    public ResponseEntity<?> updateStudent(@PathVariable Integer studentId, @RequestBody UserSecondDto dto) {

        Student updatedVehicle = studentService.updateStudent(studentId, dto);

        if (updatedVehicle != null) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
        }
    }

    @PatchMapping("changePassword/{studentId}")
    public ResponseEntity<?> changePassword(@PathVariable Integer studentId, @RequestBody UserDto dto) {
        if (studentService.changePassword(studentId, dto)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
        }
    }

}
