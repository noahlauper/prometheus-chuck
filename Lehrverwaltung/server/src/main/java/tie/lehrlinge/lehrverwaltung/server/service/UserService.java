package tie.lehrlinge.lehrverwaltung.server.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import tie.lehrlinge.lehrverwaltung.server.exception.RoleNotFoundException;
import tie.lehrlinge.lehrverwaltung.server.model.UserInfoModel;
import tie.lehrlinge.lehrverwaltung.server.model.UserModel;
import tie.lehrlinge.lehrverwaltung.server.model.entity.Role;
import tie.lehrlinge.lehrverwaltung.server.model.dto.ChangeCurrentUserDTO;
import tie.lehrlinge.lehrverwaltung.server.model.dto.ChangeUserDTO;
import tie.lehrlinge.lehrverwaltung.server.model.dto.CreateUserDTO;
import tie.lehrlinge.lehrverwaltung.server.model.dto.LoginDTO;
import tie.lehrlinge.lehrverwaltung.server.model.entity.User;
import tie.lehrlinge.lehrverwaltung.server.repository.RoleRepository;
import tie.lehrlinge.lehrverwaltung.server.repository.UserRepository;

@Service
@Slf4j
public class UserService {

  private final DaoAuthenticationProvider daoAuthenticationProvider;
  private final UserRepository userRepository;
  private final RoleRepository roleRepository;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;

  public UserService(DaoAuthenticationProvider daoAuthenticationProvider,
      UserRepository userRepository,
      RoleRepository roleRepository,
      BCryptPasswordEncoder bCryptPasswordEncoder) {
    this.daoAuthenticationProvider = daoAuthenticationProvider;
    this.userRepository = userRepository;
    this.roleRepository = roleRepository;
    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
  }

  public ResponseEntity<UserModel> login(LoginDTO loginDTO) {
    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
        loginDTO.getUsername(), loginDTO.getPassword());
    Authentication authentication;
    try {
      log.debug("start with authentication");
      authentication = daoAuthenticationProvider.authenticate(usernamePasswordAuthenticationToken);
    } catch (AuthenticationException authenticationException) {
      log.debug("Authentication failed for user with username {}", loginDTO.getUsername());
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    SecurityContext securityContext = SecurityContextHolder.getContext();
    securityContext.setAuthentication(authentication);
    User userPrincipal = (User) authentication.getPrincipal();
    UserModel userModel = new UserModel();
    userModel.setUsername(userPrincipal.getUsername());
    userModel.setRole(userPrincipal.getRole().getRolename());
    log.debug("user {} successful authenticated with role {}", userModel.getUsername(), userModel.getRole());
    return ResponseEntity.ok(userModel);
  }

  public void updatePassword(String username, String newPassword) {
    User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
    user.setPassword(bCryptPasswordEncoder.encode(newPassword));
    userRepository.save(user);
    log.debug("user: {}", username);
  }

  public void updateOwnUser(String username, ChangeCurrentUserDTO changeCurrentUserDTO) {
    User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
    user.setFirstname(changeCurrentUserDTO.getFirstname());
    user.setLastname(changeCurrentUserDTO.getLastname());
    userRepository.save(user);
  }

  public ResponseEntity<Void> updateUser(String oldUsername, ChangeUserDTO changeUserDTO) {
    if (!oldUsername.equals(changeUserDTO.getUsername()) && userRepository.findByUsername(changeUserDTO.getUsername())
        .isPresent()) {
      return new ResponseEntity<>(HttpStatus.CONFLICT);
    }
    User user = userRepository.findByUsername(oldUsername)
        .orElseThrow(() -> new UsernameNotFoundException(oldUsername));
    String roleName = changeUserDTO.getRole();
    Role role = roleRepository.findByRolename(roleName).orElseThrow(() -> new RoleNotFoundException(roleName));
    user.setUsername(changeUserDTO.getUsername());
    user.setFirstname(changeUserDTO.getFirstname());
    user.setLastname(changeUserDTO.getLastname());
    user.setRole(role);
    userRepository.save(user);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  public ResponseEntity<Void> createUser(CreateUserDTO createUserDTO) {
    if (userRepository.findByUsername(createUserDTO.getUsername()).isPresent()) {
      return new ResponseEntity<>(HttpStatus.CONFLICT);
    }
    String rolename = createUserDTO.getRole();
    Role role = roleRepository.findByRolename(rolename).orElseThrow(() -> new RoleNotFoundException(rolename));
    userRepository.save(new User(createUserDTO.getUsername(), createUserDTO.getFirstname(), createUserDTO.getLastname(),
        bCryptPasswordEncoder.encode(createUserDTO.getPassword()), role));
    log.debug("user with username: {} and role: {} is successfully created", createUserDTO.getUsername(),
        role.getRolename());
    return new ResponseEntity<>(HttpStatus.OK);
  }

  public void deleteOwnUser(String username) {
    User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
    userRepository.delete(user);
    log.debug("user: {} was successfully deleted", username);
  }

  public ResponseEntity<List<String>> getUsernamesByParameter(String parameter) {
    if (!parameter.equalsIgnoreCase("all") && !parameter.equalsIgnoreCase("not all")) {
      log.error("parameter: {} is not valid", parameter);
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    } else if (parameter.equalsIgnoreCase("all")) {
      List<String> allUsernames = new ArrayList<>();
      userRepository.findAll().forEach(user -> allUsernames.add(user.getUsername()));
      log.debug("all usernames: {}", allUsernames);
      return ResponseEntity.ok(allUsernames);
    } else {
      String rolename = "Lehrling";
      Role role = roleRepository.findByRolename(rolename).orElseThrow(() -> new RoleNotFoundException(rolename));
      List<String> usernames = new ArrayList<>();
      userRepository.findAllByRole(role).forEach(user -> usernames.add(user.getUsername()));
      log.debug("usernames of role {}: {}", rolename, usernames);
      return ResponseEntity.ok(usernames);
    }
  }

  public ResponseEntity<List<UserInfoModel>> getUserInfosWithoutParameter() {
    String roleName = "Lehrling";
    Role role = roleRepository.findByRolename(roleName).orElseThrow(() -> new RoleNotFoundException(roleName));
    List<User> apprenticeUsers = userRepository.findAllByRole(role);
    log.debug("{} users with role: {} have been found", apprenticeUsers.size(), roleName);
    return convertToUserInfoModelsByUsers(apprenticeUsers);
  }

  public ResponseEntity<List<UserInfoModel>> getUserInfosWithParameter(String parameter) {
    if (parameter.equalsIgnoreCase("all")) {
      log.debug("convet all users to userinfomodel");
      return convertToUserInfoModelsByUsers(userRepository.findAll());
    }
    log.error("parameter: {} is not set to all", parameter);
    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
  }

  private ResponseEntity<List<UserInfoModel>> convertToUserInfoModelsByUsers(List<User> users) {
    List<UserInfoModel> userInfos = new ArrayList<>();
    users.forEach(user -> userInfos.add(
        new UserInfoModel(user.getUsername(), user.getFirstname(), user.getLastname(), user.getRole().getRolename())));
    log.debug("{} users have been converted to userinfomodel", userInfos.size());
    return ResponseEntity.ok(userInfos);
  }
}
