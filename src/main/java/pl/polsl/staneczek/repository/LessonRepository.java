package pl.polsl.staneczek.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.polsl.staneczek.model.*;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface LessonRepository extends CrudRepository<Lesson, Integer> {

    List<Lesson> findAllByInstructor(Instructor instructor);

    List<Lesson> findAllByStudent(Student student);

    List<Lesson>findAllByVehicle(Vehicle vehicle);

    List<Lesson> findAllByInstructorAndDateOrderByStartTimeAsc(Instructor instructor, LocalDate date);

    List<Lesson>findAllByInstructorAndLessonStatus(Instructor instructor, LessonStatus status);

}
