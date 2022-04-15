package heavynimbus.backend.controller;

import heavynimbus.backend.controller.doc.HealthControllerDocumentation;
import heavynimbus.backend.dto.health.HealthResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health")
public class HealthController implements HealthControllerDocumentation {

  @GetMapping
  public HealthResponse getHealth() {
    return new HealthResponse("OK");
  }
}
