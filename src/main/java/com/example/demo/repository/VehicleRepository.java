package com.example.demo.repository;

import com.example.demo.model.Vehicle;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleRepository extends CrudRepository<Vehicle,Integer> {

    List<Vehicle> findAll();


}
