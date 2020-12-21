package com.example.demo.controllers;

import com.example.demo.model.Course;
import com.example.demo.service.CourseService;
import com.example.demo.service.dto.CourseDto;
import com.example.demo.service.dto.UserSecondDto;
import com.example.demo.service.dto.VehicleDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins="http://localhost:8080")
@RestController
@RequestMapping("/course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @GetMapping("/all")
    public List<CourseDto> getCourses() { return courseService.getCourseDtoList(); }

    @GetMapping("/current")
    public List<CourseDto> getCoursesByCategory()
    { return courseService.getActiveCourses(); }

    @GetMapping("/{courseId}")
    public CourseDto getCourse(@PathVariable(value = "courseId") Integer courseId) {
        return courseService.findOne(courseId);
    }

    @GetMapping("/vehicles/{courseId}")
    public List<VehicleDto> getVehiclesFromSpecificCourse(@PathVariable Integer courseId) {
        return courseService.vehicleList(courseId);
    }

    @GetMapping("/students/{courseId}")
    public List<UserSecondDto> getStudentsFromSpecificCourse(@PathVariable Integer courseId) {
        return courseService.studentList(courseId);
    }
    @GetMapping("/availableVehicles/{courseId}")
    public List<VehicleDto> getAvailableVehicles(@PathVariable Integer courseId) {
        return courseService.showAvailableVehicles(courseId);
    }

    @PostMapping
    public ResponseEntity<?> createCourse(@RequestBody CourseDto courseDto) {
        CourseDto newCourse=courseService.createCourse(courseDto);
        if (newCourse != null) {
            return new ResponseEntity<>(newCourse, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
        }
    }
    @PostMapping("/addVehicle/{courseId}/{vehicleId}")
    public ResponseEntity<HttpStatus> addVehicleToCourse(@PathVariable Integer courseId, @PathVariable Integer vehicleId) {
        if (courseService.addVehicleToCourse(courseId, vehicleId)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
        }
    }

    @PostMapping("/addStudent/{studentId}")
    public ResponseEntity<HttpStatus> addStudentToCourse(@RequestBody CourseDto course, @PathVariable Integer studentId) {
        if (courseService.addStudentToCourse(course, studentId)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
        }
    }

    @DeleteMapping("/deleteVehicle/{courseId}/{vehicleId}")
    public ResponseEntity<HttpStatus> deleteVehicleFromCourse(@PathVariable Integer courseId, @PathVariable Integer vehicleId) {
        if (courseService.deleteVehicleFromCourse(courseId, vehicleId)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
        }
    }

    @DeleteMapping("/deleteStudent/{courseId}/{studentId}")
    public ResponseEntity<HttpStatus> deleteStudent(@PathVariable Integer courseId, @PathVariable Integer studentId) {
        if (courseService.deleteStudentFromCourse(courseId, studentId)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
        }
    }

    @DeleteMapping("/{courseId}")
    public ResponseEntity<HttpStatus> deleteCourse(@PathVariable Integer courseId) {
        if (courseService.deleteCourse(courseId)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
        }
    }

    @PutMapping("/{courseId}")
    public ResponseEntity<?> updateCourse(@PathVariable Integer courseId, @RequestBody CourseDto course) {
        Course updatedCourse = courseService.updateCourse(courseId, course);
        if(updatedCourse!=null) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else  {
            return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
        }
    }
}
