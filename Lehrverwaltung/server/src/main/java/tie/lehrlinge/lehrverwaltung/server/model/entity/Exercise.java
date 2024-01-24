package tie.lehrlinge.lehrverwaltung.server.model.entity;

import java.util.List;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.CascadeType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
public class Exercise {

  @Id
  @GeneratedValue(generator = "native")
  @GenericGenerator(name = "native", strategy = "native")
  private int id;

  @NonNull
  private String title;

  @NonNull
  private String topic;

  @NonNull
  private String requirement;

  @ElementCollection
  private List<String> publicIO;

  @ElementCollection
  private List<String> privateIO;

  @OneToMany(mappedBy = "exercise", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  private List<ExerciseLink> exerciseLinks;

  @OneToMany(mappedBy = "exercise", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  private List<Run> runs;

  @OneToOne(mappedBy = "exercise")
  private ExerciseDescription exerciseDescription;

  @OneToOne(mappedBy = "exercise")
  private FileObject codeFile;
}

