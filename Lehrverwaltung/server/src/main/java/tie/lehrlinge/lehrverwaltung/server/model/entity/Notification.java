package tie.lehrlinge.lehrverwaltung.server.model.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

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
public class Notification {
  @Id
  @GeneratedValue(generator = "native")
  @GenericGenerator(name = "native", strategy = "native")
  private int id;

  @NonNull
  public Status status;

  @NonNull
  private String title;

  @NonNull
  @Column(columnDefinition = "TEXT")
  private String description;

  @NonNull
  @OneToOne
  @JoinColumn(name = "run_fk")
  private Run run;

  public Notification(@NonNull Status status, @NonNull String title, @NonNull String description) {
    this.status = status;
    this.title = title;
    this.description = description;
  }
}
