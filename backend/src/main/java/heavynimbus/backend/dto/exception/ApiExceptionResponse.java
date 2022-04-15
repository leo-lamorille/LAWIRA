package heavynimbus.backend.dto.exception;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.Map;

@Data
@Builder
public class ApiExceptionResponse {
  private HttpStatus status;
  private String message;
  private Map<String, Object> data;
}
