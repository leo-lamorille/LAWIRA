package heavynimbus.backend.controller.admin;

import heavynimbus.backend.db.stats.AttributeOptionStatsResponse;
import heavynimbus.backend.exception.NotFoundException;
import heavynimbus.backend.service.StatService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/stats")
@SecurityRequirement(name = "jwt_auth")
public class AdminStatController {
  private final StatService statService;

  @GetMapping("/command/{attributeId}")
  public List<AttributeOptionStatsResponse> getCommandOptionStatsByAttribute(
      @PathVariable UUID attributeId) throws NotFoundException {
    return statService.getCommandOptionStatsByAttribute(attributeId);
  }

  @GetMapping("/configuration/{attributeId}")
  public void getConfigurationOptionStatsByAttribute(@PathVariable UUID attributeId) {}
}
