package heavynimbus.backend.controller.user;

import heavynimbus.backend.db.command.Command;
import heavynimbus.backend.db.command.CommandStatus;
import heavynimbus.backend.dto.command.CommandResponse;
import heavynimbus.backend.service.CommandService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
}
