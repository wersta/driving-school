package pl.polsl.staneczek;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import pl.polsl.staneczek.model.Vehicle;
import pl.polsl.staneczek.model.VehicleType;
import pl.polsl.staneczek.repository.VehicleRepository;
import pl.polsl.staneczek.service.VehicleService;
import pl.polsl.staneczek.service.dto.VehicleDto;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class VehicleServiceTests {
    @Mock
    private VehicleRepository vehicleRepository;
    @InjectMocks
    private VehicleService vehicleService;

    @Test
    void shouldfindAllVehicleSuccessfully(){
        Vehicle vehicle = new Vehicle(1, "Opel", "Astra", VehicleType.CAR, Collections.emptyList());
        Vehicle vehicle2 = new Vehicle(2, "Seat", "Ibiza", VehicleType.CAR, Collections.emptyList());

        Mockito.when(vehicleRepository.findAll()).thenReturn(Arrays.asList(vehicle, vehicle2));
        List<Vehicle> vehicleList = vehicleService.vehicleList();
        assertEquals(2, vehicleList.size());
    }


    @Test
    void shouldfindVehicleSuccessfully(){
        final Vehicle vehicle= new Vehicle(1, "Opel", "Astra", VehicleType.CAR, Collections.emptyList());

        Mockito.when(vehicleRepository.findById(1)).thenReturn(java.util.Optional.of(vehicle));
        Vehicle retrivedVehicle = vehicleService.findById(1);
        assertEquals(vehicle, retrivedVehicle);
    }

    @Test
    void shouldCreateVehicleSuccessfully(){
        Vehicle vehicle = new Vehicle(1, "Opel", "Astra", VehicleType.CAR, Collections.emptyList());
        VehicleDto vehicleDto = new VehicleDto(1, "Opel", "Astra", VehicleType.CAR);

        doReturn(vehicle).when(vehicleRepository).save(any());

        Vehicle savedVehicle= vehicleService.create(vehicleDto);
        assertEquals("Astra", savedVehicle.getModel());
    }


}
