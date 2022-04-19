package heavynimbus.backend.exception;

import org.springframework.http.HttpStatus;

import java.util.Map;

public class BadRequestException extends ApiException {
  public BadRequestException(String message, Throwable cause) {
    super(
        HttpStatus.BAD_REQUEST,
        Map.of(),
        message,
        cause);
  }

  public BadRequestException(String message) {
    super(
        HttpStatus.BAD_REQUEST,
        Map.of(),
            message);
  }
}
