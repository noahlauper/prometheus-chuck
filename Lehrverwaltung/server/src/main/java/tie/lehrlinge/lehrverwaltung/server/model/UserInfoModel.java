package tie.lehrlinge.lehrverwaltung.server.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserInfoModel {

  private String username;
  private String firstname;
  private String lastname;
  private String role;
}
