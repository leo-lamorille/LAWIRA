package heavynimbus.backend.controller.user;

import heavynimbus.backend.controller.doc.CommandControllerDocumentation;
import heavynimbus.backend.db.command.CommandStatus;
import heavynimbus.backend.dto.command.CommandResponse;
import heavynimbus.backend.dto.command.CreateCommandRequest;
import heavynimbus.backend.exception.BadRequestException;
import heavynimbus.backend.exception.NotFoundException;
import heavynimbus.backend.service.CommandService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user/commands")
public class CommandController implements CommandControllerDocumentation {
  private final CommandService commandService;

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<CommandResponse> getMyCommands(
      Authentication authentication, @RequestParam(required = false) CommandStatus status) {
    return commandService.findAllByStatusAndAccountUsername(status, authentication.getName());
  }

  @GetMapping("/{commandId}")
  @ResponseStatus(HttpStatus.OK)
  public CommandResponse getCommandById(Authentication authentication, @PathVariable UUID commandId)
      throws NotFoundException {
    return commandService.findByIdAndAccountUsername(commandId, authentication.getName());
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public CommandResponse createCommand(
      Authentication authentication, @Valid @RequestBody CreateCommandRequest createCommandRequest)
      throws NotFoundException, BadRequestException {
    return commandService.createCommand(createCommandRequest, authentication);
  }

  @PutMapping("/{commandId}")
  @ResponseStatus(HttpStatus.OK)
  public CommandResponse updateCommand(
      Authentication authentication,
      @PathVariable UUID commandId,
      @Valid @RequestBody CreateCommandRequest createCommandRequest)
      throws NotFoundException, BadRequestException {
    return commandService.updateCommand(commandId, createCommandRequest, authentication);
  }

  @PutMapping("/{commandId}/buy")
  @ResponseStatus(HttpStatus.OK)
  public void buyCommand(Authentication authentication, @PathVariable UUID commandId)
      throws NotFoundException {
    commandService.buyCommand(commandId, authentication);
  }

  @DeleteMapping("/{commandId}")
  @ResponseStatus(HttpStatus.OK)
  public void deleteCommand(Authentication authentication, @PathVariable UUID commandId)
      throws NotFoundException {
    commandService.deleteByIdAndAccountUsername(commandId, authentication.getName());
  }
}
