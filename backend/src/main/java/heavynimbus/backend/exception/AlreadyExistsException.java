package heavynimbus.backend.exception;

import org.springframework.http.HttpStatus;

public class AlreadyExistsException extends ApiException {
  public AlreadyExistsException(String message) {
    super(HttpStatus.CONFLICT, null, message);
  }

  public AlreadyExistsException(String message, Throwable cause) {
    super(HttpStatus.CONFLICT, null, message, cause);
  }
}
