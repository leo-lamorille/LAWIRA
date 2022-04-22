package heavynimbus.backend.exception;

import heavynimbus.backend.dto.exception.ApiExceptionResponse;
import io.swagger.v3.oas.models.responses.ApiResponse;
import java.util.HashMap;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNullApi;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Log4j2
@RestControllerAdvice
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(BadCredentialsException.class)
  @ResponseStatus(HttpStatus.FORBIDDEN)
  public ApiExceptionResponse handleBadCredentialsException() {
    log.info("Handled login request with bad credentials");
    return ApiExceptionResponse.builder()
        .status(HttpStatus.FORBIDDEN)
        .message("Wrong username or password")
        .data(Map.of())
        .build();
  }

  @ExceptionHandler(ApiException.class)
  public ResponseEntity<ApiExceptionResponse> handleApiException(
      ApiException e, HttpServletRequest request) {
    log.info(
        "Handled API exception ({}) on {}: {}",
        e.getClass(),
        request.getRequestURI(),
        e.getMessage());
    ApiExceptionResponse response =
        ApiExceptionResponse.builder()
            .status(e.getHttpStatus())
            .message(e.getMessage())
            .data(e.getData())
            .build();
    return ResponseEntity.status(e.getHttpStatus()).body(response);
  }

  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public void handleInternalServerError(Exception e, HttpServletRequest request) {
    log.error("An internal server error has occurred on {}", request.getRequestURI(), e);
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {
    BindingResult bindingResult = ex.getBindingResult();
    var body = ApiExceptionResponse.builder().status(status).message("Argument not valid");
    Map<String, Object> errors = new HashMap<>();
    for (FieldError fieldError : bindingResult.getFieldErrors())
      errors.put(fieldError.getField(), fieldError.getDefaultMessage());
    body.data(errors);
    return ResponseEntity.status(status).body(body.build());
  }
}
