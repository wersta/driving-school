package com.example.demo.service;

import com.example.demo.model.Vehicle;
import com.example.demo.repository.CourseRepository;
import com.example.demo.repository.VehicleRepository;
import com.example.demo.service.dto.VehicleDto;
import com.example.demo.service.mapper.VehicleDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private VehicleDtoMapper mapper;

    public Vehicle findById(Integer vehicleId) {
        return vehicleRepository.findById(vehicleId).isPresent() ? vehicleRepository.findById(vehicleId).get() : null;
    }

    public VehicleDto findOne(Integer vehicleId)
    {
        Vehicle vehicle=findById(vehicleId);
        if(vehicle != null) {
            return mapper.toDto(vehicle);
        }
        return null;
    }

    public VehicleDto create(VehicleDto dto) {
        Vehicle vehicle = new Vehicle();
        vehicle.setId(dto.getId());
        vehicle.setBrand(dto.getBrand());
        vehicle.setModel(dto.getModel());
        vehicle.setVehicleType(dto.getVehicleType());
        vehicle.setCourseList(Collections.emptyList());
        vehicleRepository.save(vehicle);

        return mapper.toDto(vehicle);
    }

    public List<VehicleDto> vehicleDtoList(){
        List<Vehicle> vehicleList = vehicleRepository.findAll();

        if(vehicleList != null) {
            List<VehicleDto> vehicleDtoList = new ArrayList<>();
            for (Vehicle e : vehicleList) {
                vehicleDtoList.add(mapper.toDto(e));
            }
            return vehicleDtoList;
        }else return null;
    }

    public boolean deleteVehicle(Integer vehicleId) {

        Vehicle vehicle=findById(vehicleId);

        if(vehicle != null)
        {
            vehicle.removeCourses();
            vehicleRepository.delete(vehicle);
            return true;
        }
        return false;
    }

    public Vehicle updateVehicle(int id, VehicleDto vehicle) {
        Vehicle updatedVehicle = findById(id);
        Vehicle newVehicle = mapper.toEntity(vehicle);

        if (newVehicle.getBrand() != null)
            updatedVehicle.setBrand(newVehicle.getBrand());
        if (newVehicle.getModel() != null)
            updatedVehicle.setModel(newVehicle.getModel());
        if (newVehicle.getVehicleType() != null)
            updatedVehicle.setVehicleType(newVehicle.getVehicleType());


        vehicleRepository.save(updatedVehicle);
        return updatedVehicle;
    }
}
