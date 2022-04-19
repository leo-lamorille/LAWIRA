package heavynimbus.backend.exception;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.util.Map;

@Getter
@Setter
public abstract class ApiException extends Exception {
  protected HttpStatus httpStatus;
  protected Map<String, Object> data;

  protected ApiException(
      HttpStatus httpStatus, Map<String, Object> data, String message, Throwable cause) {
    super(message, cause);
    this.httpStatus = httpStatus;
    this.data = data;
  }
  protected ApiException(HttpStatus httpStatus, Map<String, Object> data, String message){
    super(message);
    this.httpStatus = httpStatus;
    this.data = data;
  }
}
