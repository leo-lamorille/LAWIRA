package heavynimbus.backend.controller.user;

import heavynimbus.backend.controller.doc.ConfigurationControllerDocumentation;
import heavynimbus.backend.db.configuration.Configuration;
import heavynimbus.backend.dto.configuration.ConfigurationResponse;
import heavynimbus.backend.service.ConfigurationService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user/configurations")
public class ConfigurationController implements ConfigurationControllerDocumentation {
  private final ConfigurationService configurationService;

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<ConfigurationResponse> getMyConfigurations(Authentication authentication) {
    return configurationService.findAllByUsername(authentication.getName());
  }
}
