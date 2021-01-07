package pl.polsl.staneczek.controllers;

import pl.polsl.staneczek.service.dto.VehicleDto;
import pl.polsl.staneczek.model.Vehicle;
import pl.polsl.staneczek.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("http://localhost:8080")
@RestController
@RequestMapping("vehicle")
@ResponseBody
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    @GetMapping("/all")
    public List<VehicleDto> getVehicleList() {
        return vehicleService.vehicleDtoList();
    }

    @GetMapping("/find/{vehicleId}")
    public VehicleDto getVehicle(@PathVariable(value = "vehicleId") Integer vehicleId) {
        return vehicleService.findOne(vehicleId);
    }

    @PostMapping
    public ResponseEntity<?> createVehicle(@RequestBody VehicleDto vehicleDto) {
        Vehicle newVehicle=vehicleService.create(vehicleDto);
        if (newVehicle != null) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
        }
    }

    @DeleteMapping("/{vehicleId}")
    public ResponseEntity<HttpStatus> deleteVehicle(@PathVariable Integer vehicleId) {
        if (vehicleService.deleteVehicle(vehicleId)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
        }
    }

    @PutMapping("/{vehicleId}")
    public ResponseEntity<?> updateVehicle(@PathVariable Integer vehicleId, @RequestBody VehicleDto vehicle) {
        Vehicle updatedVehicle = vehicleService.updateVehicle(vehicleId, vehicle);
        if(updatedVehicle!=null) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else  {
            return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
        }
    }

}
