package heavynimbus.backend.controller.doc;

import heavynimbus.backend.dto.configuration.ConfigurationResponse;
import heavynimbus.backend.dto.configuration.CreateConfigurationRequest;
import heavynimbus.backend.exception.BadRequestException;
import heavynimbus.backend.exception.NotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.UUID;
import javax.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@SecurityRequirement(name = "jwt_auth")
@Tag(name = "[USER] Configurations", description = "Actions related to user's configurations")
public interface ConfigurationControllerDocumentation {

  @Operation(summary = "Get all configurations related to account")
  List<ConfigurationResponse> getMyConfigurations(Authentication authentication);

  @Operation(summary = "Get configuration by id")
  ConfigurationResponse getConfigurationById(
      Authentication authentication, @PathVariable UUID configurationId) throws NotFoundException;

  @Operation(summary = "Create new configuration")
  ConfigurationResponse createConfiguration(
      Authentication authentication, @Valid CreateConfigurationRequest createConfigurationRequest)
      throws BadRequestException, NotFoundException;

  @Operation(summary = "Update existing configuration")
  ConfigurationResponse updateConfiguration(
      Authentication authentication,
      UUID configurationId,
      @Valid CreateConfigurationRequest createConfigurationRequest)
      throws NotFoundException, BadRequestException;

  @Operation(summary = "Delete existing configuration")
  void deleteConfiguration(Authentication authentication, @PathVariable UUID configurationId)
      throws NotFoundException;
}
