package tie.lehrlinge.lehrverwaltung.server.repository;

import org.springframework.data.repository.CrudRepository;

import tie.lehrlinge.lehrverwaltung.server.model.entity.Notification;
import tie.lehrlinge.lehrverwaltung.server.model.entity.Run;

import java.util.Optional;

public interface NotificationRepository extends CrudRepository<Notification, Integer> {
}
