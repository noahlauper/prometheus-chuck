package tie.lehrlinge.lehrverwaltung.server.exception;

public class ExerciseNotFoundException extends NullPointerException {

    public ExerciseNotFoundException(String id) {
        super(id);
    }
}
