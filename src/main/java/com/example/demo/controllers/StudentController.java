package com.example.demo.controllers;

import com.example.demo.model.Address;
import com.example.demo.model.Notification;
import com.example.demo.model.Student;
import com.example.demo.service.StudentService;
import com.example.demo.service.dto.CourseDto;
import com.example.demo.service.dto.LessonDto;
import com.example.demo.service.dto.UserDto;
import com.example.demo.service.dto.UserSecondDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("student")
@ResponseBody
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping("/all")
    public List<UserSecondDto> getStudents() {
        return studentService.studentToList();
    }

    @GetMapping("/{id}")
    public UserSecondDto getStudent(@PathVariable int id) {
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
    public List<Notification> getAllNotifications(@PathVariable Integer studentId) {
        return studentService.findAllNotifications(studentId);
    }

    @GetMapping("/notification/unread/{studentId}")
    public List<Notification> getUnreadNotifications(@PathVariable Integer studentId) {
        return studentService.findAllUnread(studentId);
    }

    @PostMapping
    public ResponseEntity<HttpStatus> createStudent(@RequestBody UserDto userDto) {
        if (studentService.createStudent(userDto) != null) {
            return new ResponseEntity<>(HttpStatus.OK);
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
    @PutMapping("/{studentId}")
    public ResponseEntity<?> updateStudent(@PathVariable Integer studentId, @RequestBody String firstName, String lastName, String email, String phoneNumber) {
        Student updatedVehicle = studentService.updateStudent(studentId, firstName,lastName,email,phoneNumber);
        if(updatedVehicle!=null) {
            return new ResponseEntity<>(updatedVehicle,HttpStatus.OK);
        } else  {
            return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
        }
    }
    @PutMapping("/changeAddress/{studentId}")
    public ResponseEntity<?> updateStudentAddress(@PathVariable Integer studentId, @RequestBody Address address) {
        try {
            studentService.updateStudentAddress(studentId, address);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e){
           return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
        }
    }
}
