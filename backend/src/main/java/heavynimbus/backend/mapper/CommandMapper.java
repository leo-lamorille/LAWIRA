package heavynimbus.backend.mapper;

import heavynimbus.backend.db.attributeOption.AttributeOption;
import heavynimbus.backend.db.command.Command;
import heavynimbus.backend.dto.command.CommandResponse;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.stream.Collectors;

@Component
public class CommandMapper {
  public CommandResponse commandToCommandResponse(Command command) {
    Map<String, String> values =
        command.getValues().stream()
            .collect(
                Collectors.toMap(AttributeOption::getAttributeName, AttributeOption::getValue));
    return CommandResponse.builder()
        .id(command.getId())
        .quantity(command.getQuantity())
        .status(command.getStatus())
        .values(values)
        .build();
  }
}
