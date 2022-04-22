package heavynimbus.backend.config;

import heavynimbus.backend.exception.ApiAccessDeniedHandler;
import heavynimbus.backend.exception.ApiUnauthorizedHandled;
import lombok.RequiredArgsConstructor;
import heavynimbus.backend.filter.JwtRequestFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurity extends WebSecurityConfigurerAdapter {

  public static List<String> PUBLIC_ROUTES =
      List.of(
          "/public/**",
          "/v3/api-docs/**",
          "/swagger-ui/**",
          "/swagger-ui.html",
          "/health",
          "/error/**");
  private final JwtRequestFilter jwtRequestFilter;

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Bean
  public AccessDeniedHandler accessDeniedHandler() {
    return new ApiAccessDeniedHandler();
  }

  @Bean
  public AuthenticationEntryPoint authenticationEntryPoint() {
    return new ApiUnauthorizedHandled();
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    var requestConfigurer = http.authorizeRequests();
    for (String publicRoute : PUBLIC_ROUTES) {
      requestConfigurer.antMatchers(publicRoute).permitAll();
    }
    requestConfigurer
        .antMatchers("/user/**")
        .hasAnyAuthority("USER", "ADMIN")
        .antMatchers("/admin/**")
        .hasAuthority("ADMIN")
        .anyRequest()
        .denyAll()
        .and()
        .exceptionHandling()
        .accessDeniedHandler(accessDeniedHandler())
        .authenticationEntryPoint(authenticationEntryPoint())
        .and()
        .formLogin()
        .disable()
        .csrf()
        .disable()
        .httpBasic()
        .disable()
        .logout()
        .disable()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
  }
}
