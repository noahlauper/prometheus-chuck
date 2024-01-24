package tie.lehrlinge.lehrverwaltung.server.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateRunDTO {

  int exerciseId;
  String username;
  private FileObjectDTO codeFile;
}
