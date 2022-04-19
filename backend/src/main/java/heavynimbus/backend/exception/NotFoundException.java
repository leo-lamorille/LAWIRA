package heavynimbus.backend.exception;

import org.springframework.http.HttpStatus;

import java.util.Map;

public class NotFoundException extends ApiException {
  public NotFoundException(String entity, String parameter, String value) {
    super(
        HttpStatus.NOT_FOUND,
        Map.of("entity", entity, "parameter", parameter, "value", value),
        String.format("%s with [%s = %s] not found", entity, parameter, value));
  }

  public NotFoundException(String entity, String parameter, String value, Throwable throwable) {
    super(
        HttpStatus.NOT_FOUND,
        Map.of("entity", entity, "parameter", parameter, "value", value),
        String.format("%s with [%s = %s] not found", entity, parameter, value),
        throwable);
  }
}
