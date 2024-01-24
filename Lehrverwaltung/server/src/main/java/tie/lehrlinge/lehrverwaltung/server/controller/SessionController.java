package tie.lehrlinge.lehrverwaltung.server.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/session-management/")
@Slf4j
public class SessionController {

  @GetMapping("invalidity")
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  public void invalidSession() {
    log.warn("Invalid session of user");
  }

  @GetMapping("expiration")
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  public void expiredSession() {
    log.warn("Session expired");
  }
}
