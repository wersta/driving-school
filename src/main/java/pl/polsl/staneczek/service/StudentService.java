package pl.polsl.staneczek.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.polsl.staneczek.model.*;
import pl.polsl.staneczek.repository.StudentRepository;
import pl.polsl.staneczek.service.dto.*;
import pl.polsl.staneczek.service.mapper.CourseDtoMapper;
import pl.polsl.staneczek.service.mapper.LessonDtoMapper;
import pl.polsl.staneczek.service.mapper.StudentDtoMapper;
import pl.polsl.staneczek.service.mapper.UserDtoMapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Base64;
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
    private CourseService courseService;
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

    public LoginDto createStudent(UserDto userDto) {

        userDto.setUserRole(UserRole.STUDENT);

        User user = userService.createUser(userDto);
        Student student = new Student(null, user, Collections.emptyList(), Collections.emptyList(), Collections.emptyList());
        studentRepository.save(student);
        String email=user.getEmail();

        byte[] decodedBytes = Base64.getDecoder().decode(user.getPassword());
        String userPassword = new String(decodedBytes);

        String encodeBytes = Base64.getEncoder().encodeToString((email + ":" + userPassword).getBytes());
        String token="Basic ".concat(encodeBytes);
        LoginDto loginDto=new LoginDto(token,UserRole.STUDENT);
        return loginDto;
    }

    public Student findById(Integer studentId) {
        return studentRepository.findById(studentId).isPresent() ? studentRepository.findById(studentId).get() : null;
    }

    public boolean deleteStudent(Integer studentId) {
        Student student = findById(studentId);

        if (student != null) {
            for(Course e: student.getCourseList())
            {
                e.getStudentList().remove(student);
                courseService.save(e);

            }
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

    public void check(Student student) {
        if (student != null) {
            List<Lesson> lessonList = lessonService.findByStudent(student);
            LocalDate date = LocalDate.now().plusDays(1);

            for (Lesson e : lessonList) {
                if (e.getDate().equals(date) && e.getLessonStatus().equals(LessonStatus.ACCEPTED)) {
                    String description = "Data zajęć: "+ e.getDate()+". Czas rozpoczęcia: " + e.getStartTime() + ". Miejsce spotkania: " + e.getPlace();
                    Notification notification = new Notification(null, "Przypomienie o zajęciach!", description, false);
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
                lesson = e;
            }
        }
        return lesson;
    }

    public List<Notification> findAllUnread(Integer studentId) {
        Student student = findById(studentId);
        if (student != null) {
            List<Notification> notificationList = new ArrayList<>();
            for (Notification e : student.getNotificationList()) {
                if (!e.isNotificationStatus()) notificationList.add(e);
            }
            return notificationList;
        } else {
            return null;
        }
    }
    public boolean read(Integer studentId, NotificationDto dto) {
        Student student = findById(studentId);
        if (student != null) {
            List<Notification> notificationList = new ArrayList<>();
            for (Notification e : student.getNotificationList()) {
                if (e.getId().equals(dto.getId())) {
                    e.setNotificationStatus(true);
                    notificationService.save(e);
                    return true;
                }
            }
        }
        return false;
    }

    public Student updateStudent(int id, UserSecondDto dto) {
        if (findById(id) != null) {
            Student updatedStudent = findById(id);

            Address address = addressService.findById(updatedStudent.getUser().getAddress().getId());
            address.setHomeNumber(dto.getAddress().getHomeNumber());
            address.setPostalCode(dto.getAddress().getPostalCode());
            address.setStreet(dto.getAddress().getStreet());
            address.setCity(dto.getAddress().getCity());
            addressService.saveAddress(address);

            User user = userService.findById(updatedStudent.getUser().getId());
            user.setFirstName(dto.getFirstName());
            user.setLastName(dto.getLastName());
            user.setEmail(dto.getEmail());
            user.setPhoneNumber(dto.getPhoneNumber());
            userService.save(user);

            return updatedStudent;
        } else {
            return null;
        }
    }


    public Student findByUserId(Integer userId) {
        return studentRepository.findByUser_Id(userId).isPresent() ? studentRepository.findByUser_Id(userId).get() : null;
    }

    public Integer findStudentId(String email){
        int userId=userService.findStudentOrInstructor(email);
        Student student=findByUserId(userId);
        return student.getId();
    }
    public boolean changePassword( Integer studentId, UserDto dto){
        Student student=findById(studentId);
        if(student != null) {
            String encodePassword = Base64.getEncoder().encodeToString((dto.getPassword()).getBytes());
            student.getUser().setPassword(encodePassword);
            studentRepository.save(student);
            return true;
        }else
        return false;
    }
    public void save(Student student) {
        studentRepository.save(student);
    }
}
