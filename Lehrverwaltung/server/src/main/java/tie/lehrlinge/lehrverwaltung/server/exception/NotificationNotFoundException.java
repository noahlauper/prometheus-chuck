package tie.lehrlinge.lehrverwaltung.server.exception;

public class NotificationNotFoundException extends NullPointerException {

    public NotificationNotFoundException(String id) {
        super(id);
    }
}

