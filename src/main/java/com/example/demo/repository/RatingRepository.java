package com.example.demo.repository;

import com.example.demo.model.Rating;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RatingRepository extends CrudRepository<Rating,Integer> {
    List<Rating> findAll();
}
