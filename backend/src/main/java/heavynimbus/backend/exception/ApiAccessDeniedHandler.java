package heavynimbus.backend.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import heavynimbus.backend.dto.exception.ApiExceptionResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Log4j2
public class ApiAccessDeniedHandler implements AccessDeniedHandler {

  @Override
  public void handle(
      HttpServletRequest request,
      HttpServletResponse response,
      AccessDeniedException accessDeniedException)
      throws IOException, ServletException {
    log.error("ALLALAALLALALALALAL");
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth != null) {
      log.warn(
          "User: "
              + auth.getName()
              + " attempted to access the protected URL: "
              + request.getRequestURI());
    }
    ObjectMapper mapper = new ObjectMapper();
    var body =
        ApiExceptionResponse.builder()
            .status(HttpStatus.FORBIDDEN)
            .message("You don't have permission to access to this resource")
            .data(Map.of("url", request.getRequestURI()))
            .build();
    response.getWriter().println(mapper.writeValueAsString(body));
    response.setStatus(HttpStatus.FORBIDDEN.value());
  }
}
