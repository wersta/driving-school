package pl.polsl.staneczek.repository;

import pl.polsl.staneczek.model.Vehicle;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleRepository extends CrudRepository<Vehicle,Integer> {

    List<Vehicle> findAll();

}
