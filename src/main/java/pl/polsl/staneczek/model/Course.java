package pl.polsl.staneczek.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Course {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String name;
	private double price;
	private LocalDate startDate;
	private CourseType courseType;
	private int hours;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
			name = "Course_Student",
			joinColumns = {@JoinColumn(name = "course_id")},
			inverseJoinColumns = {@JoinColumn(name = "student_id")}
	)
	private List<Student> studentList;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
			name = "Course_Vehicle",
			joinColumns = {@JoinColumn(name = "course_id")},
			inverseJoinColumns = {@JoinColumn(name = "vehicle_id")}
	)
	private List<Vehicle> vehicleList;
}
