package heavynimbus.backend.controller.user;

import heavynimbus.backend.db.command.CommandStatus;
import heavynimbus.backend.dto.command.CommandResponse;
import heavynimbus.backend.dto.command.CreateCommandRequest;
import heavynimbus.backend.exception.BadRequestException;
import heavynimbus.backend.exception.NotFoundException;
import heavynimbus.backend.service.CommandService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user/commands")
@SecurityRequirement(name = "jwt_auth")
public class CommandController {
  private final CommandService commandService;

  @GetMapping
  public List<CommandResponse> getMyCommands(
      Authentication authentication, @RequestParam(required = false) CommandStatus status) {
    return commandService.findAllByStatusAndAccountUsername(status, authentication.getName());
  }

  @GetMapping("/{commandId}")
  public CommandResponse getCommandById(Authentication authentication, @PathVariable UUID commandId)
      throws NotFoundException {
    return commandService.findByIdAndAccountUsername(commandId, authentication.getName());
  }

  @PostMapping
  public CommandResponse createCommand(
      Authentication authentication, @RequestBody CreateCommandRequest createCommandRequest)
      throws NotFoundException, BadRequestException {
    return commandService.createCommand(createCommandRequest, authentication);
  }

  @DeleteMapping("/{commandId}")
  public void deleteCommand(Authentication authentication, @PathVariable UUID commandId)
      throws NotFoundException {
    commandService.deleteByIdAndAccountUsername(commandId, authentication.getName());
  }
}
