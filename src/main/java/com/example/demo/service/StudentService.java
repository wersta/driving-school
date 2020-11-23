package com.example.demo.service;

import com.example.demo.model.*;
import com.example.demo.repository.StudentRepository;
import com.example.demo.service.dto.CourseDto;
import com.example.demo.service.dto.LessonDto;
import com.example.demo.service.dto.UserDto;
import com.example.demo.service.dto.UserSecondDto;
import com.example.demo.service.mapper.CourseDtoMapper;
import com.example.demo.service.mapper.LessonDtoMapper;
import com.example.demo.service.mapper.StudentDtoMapper;
import com.example.demo.service.mapper.UserDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private AddressService addressService;
    @Autowired
    private UserService userService;

    @Autowired
    private StudentDtoMapper mapper;

    @Autowired
    private CourseDtoMapper courseMapper;
    @Autowired
    private UserDtoMapper userMapper;
    @Autowired
    private LessonDtoMapper lessonMapper;

    @Autowired
    private LessonService lessonService;

    @Autowired
    private NotificationService notificationService;

    public Student createStudent(UserDto userDto) {
        userDto.setUserRole(UserRole.STUDENT);
        User user = userService.createUser(userDto);
        Student student = new Student(null, user, Collections.emptyList(), Collections.emptyList(), Collections.emptyList());
        studentRepository.save(student);
        return student;
    }

    public Student findById(Integer studentId) {
        return studentRepository.findById(studentId).isPresent() ? studentRepository.findById(studentId).get() : null;
    }

    public boolean deleteStudent(Integer studentId) {
        Student student = findById(studentId);

        if (student != null) {
            student.removeCourses();
            studentRepository.delete(student);
            return true;
        }
        return false;
    }

    public List<UserSecondDto> studentToList() {
        List<Student> studentList = studentRepository.findAll();

        if (studentList != null) {
            List<UserSecondDto> dtoList = new ArrayList<>();
            for (Student e : studentList) {
                dtoList.add(mapper.toDto(e));
            }
            return dtoList;
        } else return null;
    }

    public List<CourseDto> courseList(Integer studentId) {

        Student student = findById(studentId);
        if (student != null) {
            List<Course> courseList = new ArrayList<>();
            courseList = student.getCourseList();
            return courseMapper.toDtoCourseList(courseList);
        } else return null;
    }

    public List<LessonDto> lessonList(Integer studentId) {

        Student student = findById(studentId);
        if (student != null) {
            List<Lesson> lessonList = new ArrayList<>();
            lessonList = student.getLessonList();

            return lessonMapper.toDtoLessonList(lessonList);
        } else return null;
    }

    public UserSecondDto findStudent(Integer studentId) {
        Student student = findById(studentId);
        if (student != null) {
            return mapper.toDto(student);
        }
        return null;
    }

    public boolean addNotification(Student student, Notification notification) {
        if (student != null) {
            student.getNotificationList().add(notification);
            studentRepository.save(student);
            return true;
        }
        return false;
    }

    public void check(Integer studentId) {
        Student student = findById(studentId);
        if (student != null) {
            List<Lesson> lessonList = lessonService.findByStudent(student);
            LocalDate date = LocalDate.now().plusDays(1);

            for (Lesson e : lessonList) {
                if (e.getDate().equals(date)) {
                    String description = "Czas rozpoczęcia: " + e.getStartTime() + ". Miejsce spotkania: " + e.getPlace();
                    Notification notification = new Notification(null, "powiadomienie", description, false);
                    notificationService.save(notification);
                    addNotification(student, notification);
                }
            }
        }
    }

    public Lesson findbyStudent(Integer studentId, Integer lessonId) {
        Student student = findById(studentId);
        Lesson lesson = new Lesson();
        for (Lesson e : student.getLessonList()) {
            if (e.getId() == lessonId) {
                lesson=e;
            }
        }
        return lesson;
    }
    public List<Notification> findAllNotifications(Integer studentId) {

        Student student = findById(studentId);
        if(student!=null) {
            return student.getNotificationList();
        }else{
            return null;}
    }

    public List<Notification> findAllUnread(Integer studentId) {
        Student student = findById(studentId);
        if(student != null) {
            List<Notification> notificationList = new ArrayList<>();
            for (Notification e : student.getNotificationList()) {
                if (!e.isNotificationStatus()) notificationList.add(e);
            }

            return notificationList;
        }else{
            return null; }
    }

    public Student updateStudent(int id,  String firstName,String lastName,String email,String phoneNumber) {
        Student updatedStudent = findById(id);

        if (firstName!=null)
            updatedStudent.getUser().setFirstName(firstName);
        if (lastName!=null)
            updatedStudent.getUser().setLastName(lastName);
        if (email!=null)
            updatedStudent.getUser().setEmail(email);
        if (phoneNumber!=null)
            updatedStudent.getUser().setPhoneNumber(phoneNumber);

        studentRepository.save(updatedStudent);
        return updatedStudent;
    }
    public void updateStudentAddress(int id, Address address) {
        Student updatedStudent = findById(id);

        updatedStudent.getUser().setAddress(address);
        addressService.saveAddress(address);
        studentRepository.save(updatedStudent);

    }

}
