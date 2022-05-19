package heavynimbus.backend.controller.doc;

import heavynimbus.backend.dto.command.AdminCommandResponse;
import heavynimbus.backend.exception.NotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.UUID;
import org.springframework.web.bind.annotation.PathVariable;

@SecurityRequirement(name = "jwt_auth")
@Tag(
    name = "[ADMIN] Commands",
    description = "Actions related to command handling for administrator")
public interface AdminCommandControllerDocumentation {

  @Operation(summary = "Find all pending commands")
  List<AdminCommandResponse> getAllPendingCommands();

  @Operation(summary = "Validate pending command by id")
  AdminCommandResponse validateCommand(UUID commandId) throws NotFoundException;
}
