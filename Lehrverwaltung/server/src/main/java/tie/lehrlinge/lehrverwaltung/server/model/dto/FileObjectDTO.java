package tie.lehrlinge.lehrverwaltung.server.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileObjectDTO {

  private String name;
  private String type;
  private byte[] data;
}
