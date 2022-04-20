package heavynimbus.backend.controller.doc;

import heavynimbus.backend.db.command.CommandStatus;
import heavynimbus.backend.dto.command.CommandResponse;
import heavynimbus.backend.dto.command.CreateCommandRequest;
import heavynimbus.backend.exception.BadRequestException;
import heavynimbus.backend.exception.NotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.UUID;

@SecurityRequirement(name = "jwt_auth")
@Tag(name = "[USER] Commands", description = "Actions related user's commands")
public interface CommandControllerDocumentation {
  @Operation(summary = "Get user's commands", description = """
          Returns a list containing commands related to the authenticated user
          """)
  List<CommandResponse> getMyCommands(Authentication authentication, CommandStatus status);


  @Operation(summary = "Get user's command by id", description = """
          Returns the requested command related to the authenticated user
          """)
  CommandResponse getCommandById(Authentication authentication, UUID commandId)
      throws NotFoundException;

  @Operation(summary = "Create new command", description = """
          Creates a command to the authenticated user and returns it
          """)
  CommandResponse createCommand(
      Authentication authentication, CreateCommandRequest createCommandRequest)
      throws NotFoundException, BadRequestException;

  @Operation(summary = "Update existing command", description = """
          Updates the requested command related to the authenticated user and returns it
          """)
  CommandResponse updateCommand(
          Authentication authentication,
          UUID commandId,
          CreateCommandRequest createCommandRequest)
          throws NotFoundException, BadRequestException;

  @Operation(summary = "Delete command", description = """
          Deletes command related to the authenticated user
          """)
  void deleteCommand(Authentication authentication, UUID commandId)
      throws NotFoundException;
}
