package tie.lehrlinge.lehrverwaltung.server.exception;

public class UserNotFoundException extends NullPointerException {

  public UserNotFoundException(String username) {
    super(username);
  }
}
