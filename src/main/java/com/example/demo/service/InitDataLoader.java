//package com.example.demo.service;
//
//import com.example.demo.model.*;
//import com.example.demo.repository.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import javax.annotation.PostConstruct;
//import java.time.LocalDate;
//import java.time.LocalTime;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//
//@Service
//public class InitDataLoader {
//
//    @Autowired
//    private AddressRepository addressRepository;
//
//    @Autowired
//    private CourseRepository courseRepository;
//
//    @Autowired
//    private InstructorRepository instructorRepository;
//
//    @Autowired
//    private LessonRepository lessonRepository;
//
//    @Autowired
//    private NotificationRepository notificationRepository;
//
//    @Autowired
//    private RatingRepository ratingRepository;
//
//    @Autowired
//    private StudentRepository studentRepository;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private VehicleRepository vehicleRepository;
//
//    @PostConstruct
//    public void initialize() {
//        List<Address> addressList = new ArrayList<>();
//        Address address = new Address(null, "city", "123-123", "street", 5);
//        addressList.add(address);
//        addressRepository.save(address);
//
//        address = new Address(null, "city2", "123-123", "street2", 1);
//        addressList.add(address);
//        addressRepository.save(address);
//
//        List<User> userList = new ArrayList<>();
//        User user = new User(null, "user1", "user1", "user@gmail.com", "user1", "123-123-123", UserRole.STUDENT, addressList.get(0));
//        userList.add(user);
//        userRepository.save(user);
//
//        user = new User(null, "instructor", "instructor", "instructor@gmail.com", "instructor", "123-123-123", UserRole.INSTRUCTOR, addressList.get(1));
//        userList.add(user);
//        userRepository.save(user);
//
//        List<Student> studentList = new ArrayList<>();
//        Student student = new Student(null, userList.get(0), Collections.emptyList(), Collections.emptyList(), Collections.emptyList());
//        studentList.add(student);
//        studentRepository.save(student);
//
//        List<Instructor> instructorList = new ArrayList<>();
//        Instructor instructor = new Instructor(null, userList.get(1), Collections.emptyList(), Collections.emptyList(),Collections.emptyList());
//        instructorList.add(instructor);
//        instructorRepository.save(instructor);
//
//        List<Vehicle> vehicleList = new ArrayList<>();
//        Vehicle vehicle = new Vehicle(null, "Mercedes", "model", VehicleType.CAR, Collections.emptyList());
//        vehicleList.add(vehicle);
//        vehicleRepository.save(vehicle);
//
//        List<Course> courseList = new ArrayList<>();
//        Course course = new Course(null, "name1", 3000, LocalDate.now(), CourseType.B, 30, studentList, vehicleList);
//        courseRepository.save(course);
//
//        Lesson lesson = new Lesson(null,LocalDate.of(2020,11,21), LocalTime.now(), LocalTime.now().plusHours(2), "test place", LessonStatus.ACCEPTED, instructorList.get(0), studentList.get(0), vehicleList.get(0));
//        lessonRepository.save(lesson);
//
//        Lesson lesson2 = new Lesson(null,LocalDate.of(2020,11,21), LocalTime.of(12,30), LocalTime.of(16,0), "test place2", LessonStatus.ACCEPTED, instructorList.get(0), studentList.get(0), vehicleList.get(0));
//        lessonRepository.save(lesson2);
//    }
//}
