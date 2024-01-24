package tie.lehrlinge.lehrverwaltung.server.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import tie.lehrlinge.lehrverwaltung.server.model.UserInfoModel;
import tie.lehrlinge.lehrverwaltung.server.model.UserModel;
import tie.lehrlinge.lehrverwaltung.server.model.dto.ChangeCurrentUserDTO;
import tie.lehrlinge.lehrverwaltung.server.model.dto.ChangeUserDTO;
import tie.lehrlinge.lehrverwaltung.server.model.dto.CreateUserDTO;
import tie.lehrlinge.lehrverwaltung.server.model.dto.LoginDTO;
import tie.lehrlinge.lehrverwaltung.server.service.SecurityContextService;
import tie.lehrlinge.lehrverwaltung.server.service.UserService;

@RestController
@RequestMapping(value = "/user-management/")
@Log4j2
public class UserController {

  private final UserService userService;
  private final SecurityContextService securityContextService;

  public UserController(UserService userService, SecurityContextService securityContextService) {
    this.userService = userService;
    this.securityContextService = securityContextService;
  }

  @DeleteMapping("user")
  @PreAuthorize("hasAnyAuthority('Lehrling', 'Lehrmeister')")
  public void deleteOwnUser() {
    userService.deleteOwnUser(securityContextService.getRequestingUsername());
  }

  @PutMapping("password")
  @PreAuthorize("hasAnyAuthority('Lehrling', 'Lehrmeister')")
  public void updateCurrentPassword(@RequestParam String newPassword) {
    userService.updatePassword(securityContextService.getRequestingUsername(), newPassword);
  }

  @PutMapping("password/{username}")
  @PreAuthorize("hasAuthority('Lehrmeister')")
  public void updatePasswordOfUser(@PathVariable String username, @RequestParam String newPassword) {
    userService.updatePassword(username, newPassword);
  }

  @PutMapping("user")
  @PreAuthorize("hasAnyAuthority('Lehrling', 'Lehrmeister')")
  public void updateOwnUserData(@RequestBody ChangeCurrentUserDTO changeCurrentUserDTO) {
    userService.updateOwnUser(securityContextService.getRequestingUsername(), changeCurrentUserDTO);
  }

  @PutMapping("user/{oldUsername}")
  @PreAuthorize("hasAuthority('Lehrmeister')")
  public ResponseEntity<Void> updateUser(@PathVariable String oldUsername, @RequestBody ChangeUserDTO changeUserDTO) {
    return userService.updateUser(oldUsername, changeUserDTO);
  }

  @PostMapping("user")
  @PreAuthorize("hasAuthority('Lehrmeister')")
  public ResponseEntity<Void> createUser(@RequestBody CreateUserDTO createUserDTO) {
    return userService.createUser(createUserDTO);
  }

  @PostMapping(value = "login")
  public ResponseEntity<UserModel> login(@RequestBody LoginDTO loginDTO) {
    return userService.login(loginDTO);
  }

  @GetMapping(value = "logout-successful")
  public void logout() {
    log.info("User {} logged out", securityContextService.getRequestingUsername());
  }

  @GetMapping("users/usernames")
  @PreAuthorize("hasAuthority('Lehrmeister')")
  public ResponseEntity<List<String>> getUsernames(@RequestParam String parameter) {
    return userService.getUsernamesByParameter(parameter);
  }

  @GetMapping("users")
  @PreAuthorize("hasAuthority('Lehrmeister')")
  public ResponseEntity<List<UserInfoModel>> getUserInfos(@RequestParam(required = false) String parameter) {
    if (parameter != null) {
      return userService.getUserInfosWithParameter(parameter);
    }
    return userService.getUserInfosWithoutParameter();
  }
}
