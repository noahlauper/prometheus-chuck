package tie.lehrlinge.lehrverwaltung.server.exception;

public class RoleNotFoundException extends NullPointerException {

  public RoleNotFoundException(String role) {
    super(role);
  }
}

