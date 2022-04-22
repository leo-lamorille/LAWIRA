package heavynimbus.backend.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import heavynimbus.backend.dto.exception.ApiExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class ApiUnauthorizedHandled implements AuthenticationEntryPoint {
  @Override
  public void commence(
      HttpServletRequest request,
      HttpServletResponse response,
      AuthenticationException authException)
      throws IOException, ServletException {
    ObjectMapper mapper = new ObjectMapper();
    var body =
        ApiExceptionResponse.builder()
            .status(HttpStatus.UNAUTHORIZED)
            .message("You are not authorized to access to this resource")
            .data(Map.of("url", request.getRequestURI(), "method", request.getMethod()))
            .build();
    response.setContentType("application/json;charset=UTF-8");
    response.setStatus(401);
    response
        .getWriter()
        .write(mapper.writeValueAsString(body)); // my util class for creating json strings
  }
}
