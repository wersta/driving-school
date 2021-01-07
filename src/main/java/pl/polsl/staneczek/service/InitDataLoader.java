package pl.polsl.staneczek.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.polsl.staneczek.model.*;
import pl.polsl.staneczek.repository.*;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.List;

@Service
public class InitDataLoader {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private InstructorRepository instructorRepository;

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    @PostConstruct
    public void initialize() {

        List<Address> addressList = new ArrayList<>();
        Address address = new Address(null, "Katowice", "33-100", "Smolna", "53");
        addressList.add(address);
        addressRepository.save(address);

        address = new Address(null, "Malbork", "55-123", "Grunwaldzka", "11");
        addressList.add(address);
        addressRepository.save(address);

        address = new Address(null, "Gliwice", "44-100", "Mała", "34");
        addressList.add(address);
        addressRepository.save(address);

        Address address3 = new Address(null, "Bytom", "44-153", "Krótka", "45");
        addressRepository.save(address3);

        List<User> userList = new ArrayList<>();
        String pass = Base64.getEncoder().encodeToString(("user1").getBytes());
        User user = new User(null, "Michalina", "Kowalczyk", "user@gmail.com", pass, "523 432 444", UserRole.STUDENT, LocalDate.of(2020,11,25),addressList.get(0));
        userList.add(user);
        userRepository.save(user);


        String pass1 = Base64.getEncoder().encodeToString(("instructor").getBytes());
        user = new User(null, "Tomasz", "Nowak", "instructor@gmail.com", pass1, "637 555 327", UserRole.INSTRUCTOR,LocalDate.of(2020,11,25), addressList.get(1));
        userList.add(user);
        userRepository.save(user);

        String pass2 = Base64.getEncoder().encodeToString(("kamil").getBytes());
        user = new User(null, "Kamil", "Kowalski", "kamil@gmail.com", pass2, "514 459 660", UserRole.INSTRUCTOR,LocalDate.of(2020,11,25), address3);
        userList.add(user);
        userRepository.save(user);

        String passAdmin = Base64.getEncoder().encodeToString(("admin").getBytes());
        user=new User(null,"admin","admin","admin@gmail.com", passAdmin,"324 353 636", UserRole.ADMIN,LocalDate.of(2020,11,25), addressList.get(2));
        userList.add(user);
        userRepository.save(user);

        List<Student> studentList = new ArrayList<>();
        Student student = new Student(null, userList.get(0), Collections.emptyList(), Collections.emptyList(), Collections.emptyList());
        studentList.add(student);
        studentRepository.save(student);


        List<Instructor> instructorList = new ArrayList<>();
        Instructor instructor = new Instructor(null, userList.get(1), Collections.emptyList(), Collections.emptyList(),Collections.emptyList());
        Instructor instructor1 = new Instructor(null, userList.get(2), Collections.emptyList(), Collections.emptyList(),Collections.emptyList());
        instructorList.add(instructor);
        instructorRepository.save(instructor);
        instructorRepository.save(instructor1);

        Rating rating=new Rating(null,LocalDate.now(),"Bardzo dobrze poprowadzone zajęcia. Brakowało tylko czasu.",3,instructor);
        ratingRepository.save(rating);
        Rating rating1=new Rating(null,LocalDate.now(),"Najlepszy instruktor! Ma dużo cierpliwości, wszystko dokładnie tłumaczy oraz nie wprowadza nerwowej atmosfery",5,instructor);
        ratingRepository.save(rating1);


        List<Vehicle> vehicleList = new ArrayList<>();
        Vehicle vehicle = new Vehicle(null, "Mercedes-Benz", "Classic", VehicleType.CAR, Collections.emptyList());
        vehicleList.add(vehicle);
        vehicleRepository.save(vehicle);

        Vehicle vehicle2 = new Vehicle(null, "Toyota", "Yaris", VehicleType.CAR, Collections.emptyList());
        vehicleList.add(vehicle2);
        vehicleRepository.save(vehicle2);

        Vehicle vehicle1 = new Vehicle(null, "Seat", "Ibiza", VehicleType.CAR, Collections.emptyList());
        vehicleRepository.save(vehicle1);


        Course course = new Course(null, "Kurs przyspieszony", 3000, LocalDate.of(2021,02,3), CourseType.B, 30, studentList, vehicleList);
        Course course2 = new Course(null, "Kurs normalny", 2000, LocalDate.now().plusDays(8), CourseType.B, 40, Collections.emptyList(), vehicleList);
        Course course3 = new Course(null, "Kurs grudniowy", 2300, LocalDate.now().plusDays(12), CourseType.A, 30, Collections.emptyList(), vehicleList);
        Course course4 =new Course(null, "Kurs przyspiesozny", 4500, LocalDate.of(2021,01,28), CourseType.A, 30, Collections.emptyList(), vehicleList);

        courseRepository.save(course);
        courseRepository.save(course2);
        courseRepository.save(course3);
        courseRepository.save(course4);


    }
}
