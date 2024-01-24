package tie.lehrlinge.lehrverwaltung.server.model.entity;

import java.util.Date;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
public class Run {

  @Id
  @GeneratedValue(generator = "native")
  @GenericGenerator(name = "native", strategy = "native")
  @Column(name = "id", nullable = false)
  private int id;

  @NonNull
  String username;

  int runtime;

  @Temporal(TemporalType.TIMESTAMP)
  Date creationDate;

  @NonNull
  @ManyToOne
  @JoinColumn(name = "exercise_fk")
  private Exercise exercise;

  @OneToOne(mappedBy = "run")
  Notification notification;

  @NonNull
  @OneToOne(mappedBy = "run")
  private FileObject codeFile;

  @PrePersist
  public void setCreationDate() {
    this.creationDate = new Date();
  }
}
