package heavynimbus.backend.controller.doc;

import heavynimbus.backend.dto.health.HealthResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Health", description = "Application health check")
public interface HealthControllerDocumentation {

  HealthResponse getHealth();
}
