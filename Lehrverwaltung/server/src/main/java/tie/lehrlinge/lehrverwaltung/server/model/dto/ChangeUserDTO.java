package tie.lehrlinge.lehrverwaltung.server.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangeUserDTO {

  private String firstname;
  private String lastname;
  private String username;
  private String role;
}

