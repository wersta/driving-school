package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String brand;
    private String model;
    private VehicleType vehicleType;

    @ManyToMany(mappedBy = "vehicleList", fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<Course> courseList;


    public void removeCourse(Course course) {
        this.getCourseList().remove(course);
        course.getVehicleList().remove(this);
    }

    public void removeCourses() {
        for (Course course : new ArrayList<>(courseList)) {
            removeCourse(course);
        }
    }
    public void addCourse(Course course) {
        this.getCourseList().add(course);
        course.getVehicleList().add(this);
    }

}
