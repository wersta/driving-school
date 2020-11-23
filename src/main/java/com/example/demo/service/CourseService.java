package com.example.demo.service;

import com.example.demo.model.Course;
import com.example.demo.model.Student;
import com.example.demo.model.Vehicle;
import com.example.demo.repository.CourseRepository;
import com.example.demo.service.dto.CourseDto;
import com.example.demo.service.dto.UserSecondDto;
import com.example.demo.service.dto.VehicleDto;
import com.example.demo.service.mapper.CourseDtoMapper;
import com.example.demo.service.mapper.StudentDtoMapper;
import com.example.demo.service.mapper.VehicleDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private CourseDtoMapper mapper;

    @Autowired
    private VehicleDtoMapper vehicleMapper;

    @Autowired
    private StudentDtoMapper studentMapper;


    public CourseDto createCourse(CourseDto dto) {

        Course course=new Course();
        course.setName(dto.getName());
        course.setId(dto.getId());
        course.setPrice(dto.getPrice());
        course.setHours(dto.getHours());
        course.setStartDate(dto.getStartDate());
        course.setCourseType(dto.getCourseType());
        course.setVehicleList(Collections.emptyList());
        course.setStudentList(Collections.emptyList());

        courseRepository.save(course);

        return mapper.toDto(course);
    }

    public List<CourseDto> getCourseDtoList() {
        List<Course> courseList = courseRepository.findAll();
        List<CourseDto> courseDtoList = new ArrayList<>();

        for (Course e : courseList) {
            courseDtoList.add(mapper.toDto(e));
        }
        return courseDtoList;
    }

    public Course findById(Integer courseId) {
        return courseRepository.findById(courseId).isPresent() ? courseRepository.findById(courseId).get() : null;
    }

    public boolean deleteCourse(Integer courseId) {

        Course course=findById(courseId);
        if(course != null) {
            courseRepository.delete(course);
            return true;
        }
        return false;
    }

    public boolean addVehicleToCourse(Integer courseId, Integer vehicleId) {
        Course course = findById(courseId);
        Vehicle vehicle = vehicleService.findById(vehicleId);

        if (course != null && vehicle != null) {
            course.getVehicleList().add(vehicle);
            courseRepository.save(course);
            return true;
        }
        return false;
    }

    public boolean deleteVehicleFromCourse(Integer courseId, Integer vehicleId) {
        Course course = findById(courseId);
        Vehicle vehicle = vehicleService.findById(vehicleId);

        if (course != null && vehicle != null) {
            course.getVehicleList().remove(vehicle);
            courseRepository.save(course);
            return true;
        }
        return false;
    }

    public boolean deleteStudentFromCourse(Integer courseId, Integer studentId) {
        Course course = findById(courseId);
        Student student = studentService.findById(studentId);

        if (course != null && student != null) {
            course.getStudentList().remove(student);
            courseRepository.save(course);
            return true;
        }
        return false;
    }
    public boolean addStudentToCourse(Integer courseId, Integer studentId) {
        Course course = findById(courseId);
        Student student = studentService.findById(studentId);

        if (course != null && student != null) {
            course.getStudentList().add(student);
            courseRepository.save(course);
            return true;
        }
        return false;
    }

    public Course updateCourse(int id, CourseDto course) {
        Course updatedCourse = findById(id);
        Course newCourse = mapper.toEntity(course);

        if (newCourse.getName() != null)
            updatedCourse.setName(newCourse.getName());
        if (newCourse.getCourseType() != null)
            updatedCourse.setCourseType(newCourse.getCourseType());
        if (newCourse.getHours() != 0)
            updatedCourse.setHours(newCourse.getHours());
        if (newCourse.getStartDate() != null)
            updatedCourse.setStartDate(newCourse.getStartDate());
        if (newCourse.getPrice() != 0)
            updatedCourse.setPrice(newCourse.getPrice());

        courseRepository.save(updatedCourse);
        return updatedCourse;
    }

    public List<VehicleDto> vehicleList(Integer courseId) {

        Course course = findById(courseId);
        if(course != null) {
            List<Vehicle> vehicleList = new ArrayList<>();
            vehicleList = course.getVehicleList();
            return vehicleMapper.toDtoVehicleList(vehicleList);
        }else return null;
    }

    public List<UserSecondDto> studentList(Integer courseId) {

        Course course = findById(courseId);
        if(course != null) {
            List<Student> students = new ArrayList<>();
            students = course.getStudentList();
            return studentMapper.toDtoStudentList(students);
        }else return null;
    }
    public CourseDto findOne(Integer courseId)
    {
        Course course=findById(courseId);
        if(course != null) {
            return mapper.toDto(course);
        }
        return null;
    }
}
