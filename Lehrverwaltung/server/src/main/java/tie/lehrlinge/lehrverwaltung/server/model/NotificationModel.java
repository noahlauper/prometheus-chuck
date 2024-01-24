package tie.lehrlinge.lehrverwaltung.server.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class NotificationModel {
    int status;

    String title;

    String description;
}
