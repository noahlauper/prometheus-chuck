package tie.lehrlinge.lehrverwaltung.server.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class EntityExceptionHandler {

  @ExceptionHandler(UserNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public void handleUserNotFoundException(UserNotFoundException e) {
    log.error("Benutzer: {} existiert nicht in der DB", e.getMessage());
  }

  @ExceptionHandler(RoleNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public void handleRoleNotFoundException(RoleNotFoundException e) {
    log.error("Role: {} existiert nicht in der DB", e.getMessage());
  }

  @ExceptionHandler(ExerciseNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public void handleExerciseNotFoundException(ExerciseNotFoundException e) {
    log.error("Exercise mit id: {} existiert nicht in der DB", e.getMessage());
  }

  @ExceptionHandler(RunNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public void handleRunNotFoundException(RunNotFoundException e) {
    log.error("Run mit id: {} existiert nicht in der DB", e.getMessage());
  }

  @ExceptionHandler(NotificationNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public void handleNotificationNotFoundException(NotificationNotFoundException e) {
    log.error("Notifikation mit id: {} existiert nicht in der DB", e.getMessage());
  }
}
