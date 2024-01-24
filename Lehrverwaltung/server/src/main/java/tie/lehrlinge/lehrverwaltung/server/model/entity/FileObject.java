package tie.lehrlinge.lehrverwaltung.server.model.entity;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
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
public class FileObject {
  @Id
  @GeneratedValue(generator = "native")
  @GenericGenerator(name = "native", strategy = "native")
  private int id;

  @NonNull
  private String name;

  @NonNull
  @Lob
  @Column(columnDefinition = "BLOB")
  private byte[] data;

  @NonNull
  private String type;

  @OneToOne
  @JoinColumn(name = "exercise_fk")
  private Exercise exercise;

  @OneToOne
  @JoinColumn(name = "run_fk")
  private Run run;
}
