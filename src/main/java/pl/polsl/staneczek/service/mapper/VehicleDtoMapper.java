package pl.polsl.staneczek.service.mapper;

import org.springframework.stereotype.Service;
import pl.polsl.staneczek.model.Vehicle;
import pl.polsl.staneczek.service.dto.VehicleDto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class VehicleDtoMapper {

    public VehicleDto toDto(Vehicle vehicle) {
        return new VehicleDto(vehicle.getId(), vehicle.getBrand(), vehicle.getModel(), vehicle.getVehicleType());
    }

    public Vehicle toEntity(VehicleDto vehicleDto)
    {
        Vehicle vehicle=new Vehicle();
        vehicle.setId(vehicleDto.getId());
        vehicle.setBrand(vehicleDto.getBrand());
        vehicle.setModel(vehicleDto.getModel());
        vehicle.setVehicleType(vehicleDto.getVehicleType());
        vehicle.setCourseList(Collections.emptyList());

        return vehicle;
    }
    public List<VehicleDto> toDtoVehicleList(List<Vehicle>vehicleList )
    {
        List<VehicleDto> vehicleListDto = new ArrayList<>();
        vehicleList.forEach(vehicle -> vehicleListDto.add(this.toDto(vehicle)));
        return vehicleListDto;
    }
}
