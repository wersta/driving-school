package pl.polsl.staneczek.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.polsl.staneczek.model.Lesson;
import pl.polsl.staneczek.model.Vehicle;
import pl.polsl.staneczek.repository.VehicleRepository;
import pl.polsl.staneczek.service.dto.VehicleDto;
import pl.polsl.staneczek.service.mapper.VehicleDtoMapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private LessonService lessonService;
    @Autowired
    private VehicleDtoMapper mapper;

    public Vehicle findById(Integer vehicleId) {
        return vehicleRepository.findById(vehicleId).isPresent() ? vehicleRepository.findById(vehicleId).get() : null;
    }

    public VehicleDto findOne(Integer vehicleId)
    {
        Vehicle vehicle = findById(vehicleId);
        if(vehicle != null) {
            return mapper.toDto(vehicle);
        }
        return null;
    }

    public Vehicle create(VehicleDto dto) {

        Vehicle vehicle = new Vehicle();
        vehicle.setId(dto.getId());
        vehicle.setBrand(dto.getBrand());
        vehicle.setModel(dto.getModel());
        vehicle.setVehicleType(dto.getVehicleType());
        vehicle.setCourseList(Collections.emptyList());
        vehicleRepository.save(vehicle);

        return vehicle;
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
    public List<Vehicle> vehicleList(){
        List<Vehicle> vehicleList = vehicleRepository.findAll();
        if(vehicleList != null) {
            return vehicleList;
        }else return null;
    }

    public boolean deleteVehicle(Integer vehicleId) {

        Vehicle vehicle = findById(vehicleId);
        List<Lesson> lessonList = lessonService.findByVehicle(vehicle);

        int count=0;
        if (vehicle != null){

            if(lessonList!=null){
                for(Lesson e: lessonList){
                    if(e.getDate().isBefore(LocalDate.now())){
                        count++;
                    }
                }
                if(count==lessonList.size()){
                    for(Lesson e: lessonList){
                        lessonService.delete(e);
                    }
                    vehicle.removeCourses();
                    vehicleRepository.delete(vehicle);
                    return true;
                }
                return false;
            }
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
