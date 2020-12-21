package pl.polsl.staneczek.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.polsl.staneczek.model.*;
import pl.polsl.staneczek.repository.LessonRepository;
import pl.polsl.staneczek.service.dto.LessonDto;
import pl.polsl.staneczek.service.mapper.LessonDtoMapper;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class LessonService {

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private LessonDtoMapper lessonMapper;

    @Autowired
    private InstructorService instructorService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private VehicleService vehicleService;

    public Lesson findById(Integer lessonId) {
        return lessonRepository.findById(lessonId).isPresent() ? lessonRepository.findById(lessonId).get() : null;
    }

    public LessonDto findLesson(Integer ratingId)
    {
        Lesson lesson=findById(ratingId);
        if(lesson != null) {
            return lessonMapper.toDto(lesson);
        }
        return null;
    }
    public void save(Lesson lesson) {
        lessonRepository.save(lesson);
    }
    public void delete(Lesson lesson) {
        lessonRepository.delete(lesson);
    }

    public LessonDto createLesson(LessonDto lessonDto, Integer studentId) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm");
        Instructor instructor = instructorService.findById(lessonDto.getInstructorId());
        Student student = studentService.findById(studentId);
        Vehicle vehicle = vehicleService.findById(lessonDto.getVehicleId());

        LocalTime endTime= LocalTime.parse(lessonDto.getEndTime(),format);
        LocalTime startTime= LocalTime.parse(lessonDto.getStartTime(),format);

        Lesson lesson = new Lesson();
        lesson.setId(lessonDto.getId());
        lesson.setEndTime( endTime);
        lesson.setDate(lessonDto.getDate());
        lesson.setStartTime(startTime );
        lesson.setPlace(lessonDto.getPlace());
        lesson.setLessonStatus(LessonStatus.WAITING);
        lesson.setVehicle(vehicle);
        lesson.setInstructor(instructor);
        lesson.setStudent(student);

        lessonRepository.save(lesson);
        return lessonMapper.toDto(lesson);
    }

    public List<Lesson> findByInstructor(Instructor instructor) {
        return lessonRepository.findAllByInstructor(instructor);
    }
    public List<Lesson> findByVehicle(Vehicle vehicle) {
        return lessonRepository.findAllByVehicle(vehicle);
    }

    public List<Lesson> findByStudent(Student student) {
        return lessonRepository.findAllByStudent(student);
    }

    public LessonDto acceptLesson(Integer lessonId) {
        Lesson updatedLesson=findById(lessonId);
        if(updatedLesson!=null) {
            updatedLesson.setLessonStatus(LessonStatus.ACCEPTED);
            lessonRepository.save(updatedLesson);
            return lessonMapper.toDto(updatedLesson);
        }else
            {return null;}

    }
    public LessonDto denyLesson(Integer lessonId) {
        Lesson updatedLesson=findById(lessonId);
        if(updatedLesson!=null) {
            updatedLesson.setLessonStatus(LessonStatus.DENIED);
            lessonRepository.save(updatedLesson);
            return lessonMapper.toDto(updatedLesson);
        }else {return null;}

    }


    public List<Lesson> findAllByInstructorAndDateOrderByStartTime(Instructor instructor, LocalDate localDate) {
        return lessonRepository.findAllByInstructorAndDateOrderByStartTimeAsc(instructor, localDate);
    }
    public List<Lesson> findAllByInstructorAndLessonStatusAccepted(Instructor instructor, LessonStatus status) {
        return lessonRepository.findAllByInstructorAndLessonStatus(instructor, status);
    }
    public List<Lesson> findAllByInstructorAndLessonStatusWaiting(Instructor instructor,LessonStatus status) {
        return lessonRepository.findAllByInstructorAndLessonStatus(instructor, status);
    }

}
