package tie.lehrlinge.lehrverwaltung.server.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import tie.lehrlinge.lehrverwaltung.server.model.entity.ExerciseDescription;

@Repository
public interface ExerciseDescriptionRepository extends CrudRepository<ExerciseDescription, Integer> {

}
