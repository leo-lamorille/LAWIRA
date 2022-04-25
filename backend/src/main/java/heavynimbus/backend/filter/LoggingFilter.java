package heavynimbus.backend.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Log4j2
@Component
public class LoggingFilter implements HandlerInterceptor {

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
    log.info(
        "Incoming request {} {} handled by {}",
        request.getMethod(),
        request.getRequestURI(),
        handler);
    return true;
  }

  @Override
  public void afterCompletion(
      HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
      throws Exception {
    log.info(
        "{} {} --> {}", request.getMethod(), request.getRequestURI(), response.getStatus(), ex);
  }
}
