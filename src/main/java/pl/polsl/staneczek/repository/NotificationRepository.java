package pl.polsl.staneczek.repository;

import pl.polsl.staneczek.model.Notification;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends CrudRepository<Notification,Integer> {

}
