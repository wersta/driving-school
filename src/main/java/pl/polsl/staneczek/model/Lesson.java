package pl.polsl.staneczek.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private String place;
    private LessonStatus lessonStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    private Instructor instructor;

    @ManyToOne(fetch = FetchType.LAZY)
    private Student student;

    @OneToOne(fetch = FetchType.LAZY)
    private Vehicle vehicle;
}
