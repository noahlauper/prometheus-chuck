package tie.lehrlinge.lehrverwaltung.server.exception;

public class RunNotFoundException extends NullPointerException {

    public RunNotFoundException(String id) {
        super(id);
    }

}
