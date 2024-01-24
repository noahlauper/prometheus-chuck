package tie.lehrlinge.lehrverwaltung.server.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import tie.lehrlinge.lehrverwaltung.server.model.entity.Role;
import tie.lehrlinge.lehrverwaltung.server.model.entity.User;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

  Optional<User> findByUsername(String username);

  List<User> findAll();

  List<User> findAllByRole(Role role);
}
