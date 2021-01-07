package pl.polsl.staneczek.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.polsl.staneczek.model.Vehicle;

import java.util.List;
import java.util.Optional;

@Repository
public interface VehicleRepository extends CrudRepository<Vehicle,Integer> {

    List<Vehicle> findAll();
    Optional<Vehicle> findById(Integer id);

}
