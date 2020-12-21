package com.example.demo.service;

import com.example.demo.model.*;
import com.example.demo.repository.InstructorRepository;
import com.example.demo.repository.RatingRepository;
import com.example.demo.service.dto.*;
import com.example.demo.service.mapper.InstructorDtoMapper;
import com.example.demo.service.mapper.LessonDtoMapper;
import com.example.demo.service.mapper.RatingDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class InstructorService {

    @Autowired
    private InstructorRepository instructorRepository;
    @Autowired
    private RatingRepository ratingRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private AddressService addressService;
    @Autowired
    private RatingDtoMapper ratingMapper;

    @Autowired
    private InstructorDtoMapper instructorMapper;

    @Autowired
    private LessonDtoMapper lessonMapper;

    @Autowired
    private LessonService lessonService;

    @Autowired
    private NotificationService notificationService;

    public Instructor findById(Integer instructorId) {
        return instructorRepository.findById(instructorId).isPresent() ? instructorRepository.findById(instructorId).get() : null;
    }

    public Instructor createInstructor(UserDto userDto) {
        userDto.setUserRole(UserRole.INSTRUCTOR);
        User user = userService.createUser(userDto);
        Instructor instructor = new Instructor(null, user, Collections.emptyList(), Collections.emptyList(), Collections.emptyList());
        instructorRepository.save(instructor);
        return instructor;
    }

    public boolean deleteInstructor(Integer instructorId) {
        Instructor instructor = findById(instructorId);

        if (instructor != null) {

            instructorRepository.delete(instructor);
            return true;
        }
        return false;
    }
    public Instructor updateInstructor(int id, UserSecondDto dto) {
        if (findById(id) != null) {
            Instructor updatedInstructor = findById(id);

            Address address = addressService.findById(updatedInstructor.getUser().getAddress().getId());
            address.setHomeNumber(dto.getAddress().getHomeNumber());
            address.setPostalCode(dto.getAddress().getPostalCode());
            address.setStreet(dto.getAddress().getStreet());
            address.setCity(dto.getAddress().getCity());
            addressService.saveAddress(address);

            User user = userService.findById(updatedInstructor.getUser().getId());
            user.setFirstName(dto.getFirstName());
            user.setLastName(dto.getLastName());
            user.setEmail(dto.getEmail());
            user.setPhoneNumber(dto.getPhoneNumber());
            userService.save(user);

            return updatedInstructor;
        } else {
            return null;
        }
    }
    public List<RatingDto> ratingList(Integer instructorId) {
        Instructor instructor = findById(instructorId);
        if (instructor != null) {
            List<Rating> ratingList;
            ratingList = instructor.getRatingList();
            return ratingMapper.toDtoRatingList(ratingList);
        } else return null;
    }

    public float averageRating(Integer instructorId) {
        Instructor instructor = findById(instructorId);
        int sumRating=0;
        if (instructor != null) {
            for(Rating rating: instructor.getRatingList()){
                sumRating=sumRating+rating.getStarsNumber();
            }
        }
        if(instructor.getRatingList().isEmpty()){
            return 0;
        }else{
         float averageRating = sumRating/instructor.getRatingList().size();
        return averageRating;}
    }

//    public List<RatingDto> findInstructorRatingsByEmail(String email) {
//        List<Instructor> instructorList = instructorRepository.findAll();
//        for (Instructor e : instructorList) {
//            if (e.getUser().getEmail().equals(email)) {
//                return ratingMapper.toDtoRatingList(e.getRatingList());
//            }
//        }
//        return null;
//    }
//

    public boolean acceptLesson(Integer instructorId, LessonDto dto) {
        Instructor instructor = findById(instructorId);
        if (instructor != null) {
            List<Lesson> lessonList = new ArrayList<>();
            for (Lesson e : instructor.getLessonList()) {
                if (e.getId().equals(dto.getId())) {
                    e.setLessonStatus(LessonStatus.ACCEPTED);
                    notificationService.createStudentNotification(dto.getStudentId(), dto.getId());
                    lessonService.save(e);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean denyLesson(Integer instructorId, LessonDto dto) {
        Instructor instructor = findById(instructorId);
        if (instructor != null) {
            List<Lesson> lessonList = new ArrayList<>();
            for (Lesson e : instructor.getLessonList()) {
                if (e.getId().equals(dto.getId())) {
                    e.setLessonStatus(LessonStatus.DENIED);
                    lessonService.save(e);
                    notificationService.createStudentNotification(dto.getStudentId(), dto.getId());
                    return true;
                }
            }
        }
        return false;
    }


    public List<LessonDto> lessonList(Integer instructorId) {
        Instructor instructor = findById(instructorId);
        if (instructor != null) {
            List<Lesson> lessonList = new ArrayList<>();
            lessonList = instructor.getLessonList();
            return lessonMapper.toDtoLessonList(lessonList);
        } else return null;
    }

    public List<LessonDto>acceptedLessonList(Integer instructorId){
        Instructor instructor = findById(instructorId);
        if(instructor != null){
            List<Lesson> lessonList = new ArrayList<>();
            lessonList=lessonService.findAllByInstructorAndLessonStatusAccepted(instructor, LessonStatus.ACCEPTED);
            return lessonMapper.toDtoLessonList(lessonList);
        }else return null;
    }
    public List<LessonDto>waitingLessonList(Integer instructorId){
        Instructor instructor = findById(instructorId);
        if(instructor != null){
            List<Lesson> lessonList = new ArrayList<>();
            lessonList=lessonService.findAllByInstructorAndLessonStatusWaiting(instructor, LessonStatus.WAITING);
            return lessonMapper.toDtoLessonList(lessonList);
        }else return null;
    }

    public List<UserSecondDto> instructorToList() {
        List<Instructor> instructorList = instructorRepository.findAll();
        List<UserSecondDto> dtoList = new ArrayList<>();
        for (Instructor e : instructorList) {
            dtoList.add(instructorMapper.toDto(e));
        }
        return dtoList;
    }

    public UserSecondDto findInstructor(Integer instructorId) {
        Instructor instructor = findById(instructorId);
        if (instructor != null) {
            return instructorMapper.toDto(instructor);
        }
        return null;
    }


    public void addNotification(Instructor instructor, Notification notification) {
        instructor.getNotificationList().add(notification);
        instructorRepository.save(instructor);
    }

    public boolean read(Integer instructorId, NotificationDto dto) {
        Instructor instructor = findById(instructorId);
        if (instructor != null) {
            List<Notification> notificationList = new ArrayList<>();
            for (Notification e : instructor.getNotificationList()) {
                if (e.getId().equals(dto.getId())) {
                    e.setNotificationStatus(true);
                    notificationService.save(e);
                    return true;
                }
            }
        }
        return false;
    }

    public void check(Instructor instructor) {
        if (instructor != null) {
            List<Lesson> lessonList = lessonService.findByInstructor(instructor);
            LocalDate date = LocalDate.now().plusDays(1);

            for (Lesson e : lessonList) {
                if (e.getDate().equals(date)&& e.getLessonStatus().equals(LessonStatus.ACCEPTED)) {
                    String description = "Czas rozpoczęcia: " + e.getStartTime() + ". Miejsce rozpoczęcia: " + e.getPlace();
                    Notification notification = new Notification(null, "Powiadomienie o zajęciach!", description, false);
                    notificationService.save(notification);
                    addNotification(instructor, notification);
                }
            }
        }
    }

//    public List<Notification> findAllNotifications(Integer instructorId) {
//
//        Instructor instructor = findById(instructorId);
//        if (instructor != null) {
//            return instructor.getNotificationList();
//        } else {
//            return null;
//        }
//    }

    public List<Notification> findAllUnread(Integer instructorId) {
        Instructor instructor = findById(instructorId);
        if (instructor != null) {
            List<Notification> notificationList = new ArrayList<>();
            for (Notification e : instructor.getNotificationList()) {
                if (!e.isNotificationStatus()) notificationList.add(e);
            }
            return notificationList;
        } else {
            return null;
        }
    }

    public Instructor findByUserId(Integer userId) {
        return instructorRepository.findByUser_Id(userId).isPresent() ? instructorRepository.findByUser_Id(userId).get() : null;
    }


//    // godziny
//    public List<String> freeHours(DateDto dto) {
//        Instructor instructor = findById(dto.getInstructorId());
////        List<Lesson> lessonList = instructor.getLessonList();
//
//
//        LocalDate date = LocalDate.parse(dto.getDate());
//
//        List<Lesson> temp = lessonService.findAllByInstructorAndDateOrderByStartTime(instructor, date);
//        List<String> temp2 = new ArrayList<>();
//
//        if (temp.isEmpty()) {
//            temp2.add("08:00 - 16:00");
//        } else {
//
//            LocalTime time = LocalTime.parse("08:00");
//
//            for (Lesson e : temp) {
//                LocalTime startTime = e.getStartTime();
//                LocalTime endTime = e.getEndTime();
//
//                if (time.isBefore(startTime)){
//                    temp2.add(time.toString() + " - " + startTime.toString());
//                    time = endTime;
//                }else{
//                    time = endTime;
//                }
//            }
//
//            if (time.isBefore(LocalTime.parse("16:00"))){
//                temp2.add(time.toString() + " - " + "16:00");
//            }
//        }
//
//        return temp2;
//    }

    // godziny
    public ArrayList<ArrayList<Integer> > freeHours(DateDto dto) {
        Instructor instructor = findById(dto.getInstructorId());


        LocalDate date = LocalDate.parse(dto.getDate());

        List<Lesson> lesssonList = lessonService.findAllByInstructorAndDateOrderByStartTime(instructor, date);
        ArrayList<ArrayList<Integer> > outputList = new ArrayList<ArrayList<Integer> >();

        if (lesssonList.isEmpty()) {
            ArrayList<Integer> a1 = new ArrayList<Integer>();
            for (int i = 8; i <= 16; i++) {
                a1.add(i);
            }
            outputList.add(a1);
        } else {

            Integer startTime = 8;

            for (Lesson lesson : lesssonList) {
                Integer lessonStartTime = lesson.getStartTime().getHour();
                Integer lessonEndTime = lesson.getEndTime().getHour();

                if (startTime < lessonStartTime){
                    ArrayList<Integer> a1 = new ArrayList<Integer>();
                    for (int i = startTime; i <= lessonStartTime; i++) {
                        a1.add(i);
                    }
                    outputList.add(new ArrayList<>(a1));

                    startTime = lessonEndTime;
                }else{
                    startTime = lessonEndTime;
                }
            }

            if (startTime < 16){
                ArrayList<Integer> a1 = new ArrayList<Integer>();
                for (int i = startTime; i <= 16; i++) {
                    a1.add(i);
                }
                outputList.add(new ArrayList<>(a1));
            }
        }

        return outputList;
    }



    public boolean changePassword( Integer instructorId, UserDto dto){
        Instructor instructor=findById(instructorId);
        if(instructor != null) {
            instructor.getUser().setPassword(dto.getPassword());
            instructorRepository.save(instructor);
            return true;
        }else
            return false;
    }
}