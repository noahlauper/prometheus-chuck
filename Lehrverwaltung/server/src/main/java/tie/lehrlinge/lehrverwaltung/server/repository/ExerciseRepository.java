package tie.lehrlinge.lehrverwaltung.server.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import tie.lehrlinge.lehrverwaltung.server.model.entity.Exercise;

@Repository
public interface ExerciseRepository extends CrudRepository<Exercise, Integer> {

  List<Exercise> findAll();
}
