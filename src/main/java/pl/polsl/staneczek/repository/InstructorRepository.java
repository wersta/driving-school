package pl.polsl.staneczek.repository;

import pl.polsl.staneczek.model.Instructor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InstructorRepository extends CrudRepository<Instructor,Integer> {
    List<Instructor> findAll();

    Optional<Instructor> findByUser_Id(Integer userId);
}
