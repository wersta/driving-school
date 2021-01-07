package pl.polsl.staneczek.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.polsl.staneczek.model.*;
import pl.polsl.staneczek.repository.InstructorRepository;
import pl.polsl.staneczek.service.dto.*;
import pl.polsl.staneczek.service.mapper.InstructorDtoMapper;
import pl.polsl.staneczek.service.mapper.LessonDtoMapper;
import pl.polsl.staneczek.service.mapper.RatingDtoMapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.List;

@Service
public class InstructorService {

    @Autowired
    private InstructorRepository instructorRepository;
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
        Instructor instructor = findById(instructorId);
        if(instructor != null) {
            String encodePassword = Base64.getEncoder().encodeToString((dto.getPassword()).getBytes());
            instructor.getUser().setPassword(encodePassword);
            instructorRepository.save(instructor);
            return true;
        }else
            return false;
    }
}