package tie.lehrlinge.lehrverwaltung.server.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SecurityContextService {
  public String getRequestingUsername() {
    return SecurityContextHolder.getContext().getAuthentication().getName();
  }
}
