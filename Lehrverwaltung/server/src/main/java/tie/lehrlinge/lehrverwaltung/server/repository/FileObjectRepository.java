package tie.lehrlinge.lehrverwaltung.server.repository;

import org.springframework.data.repository.CrudRepository;

import org.springframework.stereotype.Repository;
import tie.lehrlinge.lehrverwaltung.server.model.entity.FileObject;

@Repository
public interface FileObjectRepository extends CrudRepository<FileObject, Integer>  {

}