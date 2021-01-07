package pl.polsl.staneczek.service.mapper;

import pl.polsl.staneczek.service.dto.CourseDto;
import pl.polsl.staneczek.model.Course;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class CourseDtoMapper {

    public CourseDto toDto(Course course){
        return new CourseDto(course.getId(), course.getName(),course.getPrice(), course.getStartDate(), course.getCourseType(), course.getHours());
    }

    public List<CourseDto> toDtoCourseList(List<Course>courseList )
    {
        List<CourseDto> courseListDto = new ArrayList<>();
        courseList.forEach(course -> courseListDto.add(this.toDto(course)));
        return courseListDto;
    }


    public Course toEntity(CourseDto courseDto)
    {
        Course course=new Course();
        course.setName(courseDto.getName());
        course.setId(courseDto.getId());
        course.setPrice(courseDto.getPrice());
        course.setStartDate(courseDto.getStartDate());
        course.setCourseType(courseDto.getCourseType());
        course.setHours(courseDto.getHours());
        course.setVehicleList(Collections.emptyList());
        course.setStudentList(Collections.emptyList());

        return course;
    }
}
