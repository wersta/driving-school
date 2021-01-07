package pl.polsl.staneczek;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import pl.polsl.staneczek.model.Course;
import pl.polsl.staneczek.model.CourseType;
import pl.polsl.staneczek.repository.CourseRepository;
import pl.polsl.staneczek.service.CourseService;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class CourseServiceTests {
    @Mock
    private CourseRepository courseRepository;
    @InjectMocks
    private CourseService courseService;

    @Test
    void shouldfindAllCoursesSuccessfully(){
        Course course = new Course(1, "Kurs 1", 3000, LocalDate.of(2020,12,3), CourseType.B, 30, Collections.emptyList(), Collections.emptyList());
        Course course2 = new Course(2, "Kurs 2", 3000, LocalDate.of(2020,12,3), CourseType.B, 30, Collections.emptyList(), Collections.emptyList());

        Mockito.when(courseRepository.findAll()).thenReturn(Arrays.asList(course, course2));
        List<Course> courseList = courseService.courseList();
        assertEquals(2, courseList.size());
    }


    @Test
    void shouldfindVehicleSuccessfully(){
        final Course course= new Course(1, "Kurs 1", 3000, LocalDate.of(2020,12,3), CourseType.B, 30, Collections.emptyList(), Collections.emptyList());

        Mockito.when(courseRepository.findById(1)).thenReturn(java.util.Optional.of(course));
        Course retrivedCourse = courseService.findById(1);
        assertEquals(course, retrivedCourse);
    }


}
