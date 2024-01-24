package tie.lehrlinge.lehrverwaltung.server.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RunModel {

    private String username;

    private String date;

    private int runtime;
    private String name;
    private byte[] data;
    private String type;
}
