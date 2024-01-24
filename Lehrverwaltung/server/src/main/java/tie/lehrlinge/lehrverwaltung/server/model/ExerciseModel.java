package tie.lehrlinge.lehrverwaltung.server.model;

import java.util.List;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class ExerciseModel {
  private  @NonNull int id;

  private @NonNull String title;
  private @NonNull String language;
  private @NonNull String requirement;
  private String description;
  private @NonNull List<String> links;
  private byte[] textFile;
  private String name;
  private byte[] data;
  private String type;
}
