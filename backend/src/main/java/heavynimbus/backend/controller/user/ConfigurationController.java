package heavynimbus.backend.controller.user;

import heavynimbus.backend.controller.doc.ConfigurationControllerDocumentation;
import heavynimbus.backend.db.configuration.Configuration;
import heavynimbus.backend.dto.configuration.ConfigurationResponse;
import heavynimbus.backend.dto.configuration.CreateConfigurationRequest;
import heavynimbus.backend.exception.BadRequestException;
import heavynimbus.backend.exception.NotFoundException;
import heavynimbus.backend.service.ConfigurationService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.util.UUID;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

  @GetMapping("/{configurationId}")
  @ResponseStatus(HttpStatus.OK)
  public ConfigurationResponse getConfigurationById(
      Authentication authentication, @PathVariable UUID configurationId) throws NotFoundException {
    return configurationService.findByIdAndUsername(configurationId, authentication.getName());
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public ConfigurationResponse createConfiguration(
      Authentication authentication,
      @Valid @RequestBody CreateConfigurationRequest createConfigurationRequest)
      throws BadRequestException, NotFoundException {
    return configurationService.createConfiguration(createConfigurationRequest, authentication);
  }

  @PutMapping("/{configurationId}")
  public ConfigurationResponse updateConfiguration(
      Authentication authentication,
      @PathVariable UUID configurationId,
      @RequestBody CreateConfigurationRequest createConfigurationRequest)
      throws NotFoundException, BadRequestException {
    return configurationService.updateConfiguration(
        configurationId, createConfigurationRequest, authentication);
  }

  @DeleteMapping("/{configurationId}")
  public void deleteConfiguration(Authentication authentication, @PathVariable UUID configurationId)
      throws NotFoundException {
    configurationService.deleteByIdAndUsername(configurationId, authentication.getName());
  }
}
