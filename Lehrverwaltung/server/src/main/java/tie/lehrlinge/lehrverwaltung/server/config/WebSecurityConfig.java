package tie.lehrlinge.lehrverwaltung.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import tie.lehrlinge.lehrverwaltung.server.service.CustomUserDetailsService;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig {

  private final CustomUserDetailsService customUserDetailsService;

  public WebSecurityConfig(CustomUserDetailsService customUserDetailsService) {
    this.customUserDetailsService = customUserDetailsService;
  }

  @Bean
  protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

    http.httpBasic();

    http.cors()
        .configurationSource(corsConfigurationSource())
        .and()
        .csrf()
        .disable();

    http.exceptionHandling()
        .authenticationEntryPoint(new HttpStatusEntryPoint((HttpStatus.UNAUTHORIZED)));

    http.logout()
        .logoutUrl("/user-management/logout")
        .logoutSuccessUrl("/user-management/logout-successful")
        .invalidateHttpSession(true)
        .deleteCookies("JSESSIONID");

    http.sessionManagement()
        .invalidSessionUrl("/session-management/invalidity")
        .maximumSessions(1)
        .expiredUrl("/session-management/expiration");

    http.authorizeHttpRequests((authorize) -> {
      authorize.requestMatchers("/user-management/**", "/session-management/**", "time-management/**",
              "/special-date-management/special-dates/**", "/exercise-management/exercises/**", "/run/**", "/actuator/**")
          .permitAll()
          .anyRequest()
          .authenticated();
    });

    return http.build();
  }

  @Bean
  public DaoAuthenticationProvider daoAuthenticationProvider() {
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    authProvider.setPasswordEncoder(bCryptPasswordEncoder());
    authProvider.setUserDetailsService(customUserDetailsService);
    return authProvider;
  }

  @Bean
  public BCryptPasswordEncoder bCryptPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.addAllowedOrigin("http://localhost:8100");
    configuration.addAllowedMethod("*");
    configuration.addAllowedHeader("*");
    configuration.setAllowCredentials(true);
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }
}
