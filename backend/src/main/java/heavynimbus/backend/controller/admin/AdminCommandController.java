package heavynimbus.backend.controller.admin;

import heavynimbus.backend.dto.command.AdminCommandResponse;
import heavynimbus.backend.exception.NotFoundException;
import heavynimbus.backend.service.CommandService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/commands")
public class AdminCommandController {

  private final CommandService commandService;

  @GetMapping
  public List<AdminCommandResponse> getAllPendingCommands() {
    return commandService.findAllPending();
  }

  @PutMapping("/{commandId}/validate")
  public AdminCommandResponse validateCommand(@PathVariable UUID commandId)
      throws NotFoundException {
    return commandService.validateCommand(commandId);
  }
}
