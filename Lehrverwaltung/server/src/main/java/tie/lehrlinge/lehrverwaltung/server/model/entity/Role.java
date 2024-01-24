package tie.lehrlinge.lehrverwaltung.server.model.entity;

import java.util.List;

import org.hibernate.annotations.GenericGenerator;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Role {

  @Id
  @GeneratedValue(generator = "native")
  @GenericGenerator(name = "native", strategy = "native")
  private int id;

  private String rolename;

  @OneToMany(mappedBy = "role")
  private List<User> users;
}

