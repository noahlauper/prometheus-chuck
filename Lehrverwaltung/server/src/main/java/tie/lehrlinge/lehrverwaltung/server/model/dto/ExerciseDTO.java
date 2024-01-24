package tie.lehrlinge.lehrverwaltung.server.model.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExerciseDTO {

  private String title;
  private String language;
  private String requirement;
  private String description;
  private List<String> links;
  private String privateInput;
  private String publicInput;
  private String publicOutput;
  private String privateOutput;
  private FileObjectDTO codeFile;
}
