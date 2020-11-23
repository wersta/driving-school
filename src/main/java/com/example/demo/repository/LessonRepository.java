package com.example.demo.repository;

import com.example.demo.model.Instructor;
import com.example.demo.model.Lesson;
import com.example.demo.model.Student;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LessonRepository extends CrudRepository<Lesson, Integer> {

    List<Lesson> findAllByInstructor(Instructor instructor);

    List<Lesson> findAllByStudent(Student student);

}
