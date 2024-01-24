package tie.lehrlinge.lehrverwaltung.server.repository;

import org.springframework.data.repository.CrudRepository;

import org.springframework.stereotype.Repository;
import tie.lehrlinge.lehrverwaltung.server.model.entity.Exercise;
import tie.lehrlinge.lehrverwaltung.server.model.entity.Run;

import java.util.*;


@Repository
public interface RunRepository extends CrudRepository<Run, Integer> {
    Optional<Run> findFirstByUsernameAndExerciseOrderByCreationDateDesc(String username, Exercise exercise);

    List<Run> findDistinctTop2ByUsernameAndExerciseOrderByCreationDateDesc(String username , Exercise exercise);

}
