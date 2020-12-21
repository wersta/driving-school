package pl.polsl.staneczek.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private User user;

    @ManyToMany(mappedBy = "studentList", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<Course> courseList;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Lesson> lessonList;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Notification> notificationList;


//    public void removeCourse(Course course) {
//        this.getCourseList().remove(course);
//        course.getStudentList().remove(this);
//    }
//
//    public void removeCourses() {
//        for (Course course : new ArrayList<>(courseList)) {
//            removeCourse(course);
//        }
//    }
}
