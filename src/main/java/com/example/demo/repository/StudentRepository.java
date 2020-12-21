package com.example.demo.repository;

import com.example.demo.model.Student;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends CrudRepository<Student, Integer> {

    List<Student> findAll();

    Optional<Student> findByUser_Id(Integer userId);
    Optional<Student> findStudentByUserEmail(String email);
}
