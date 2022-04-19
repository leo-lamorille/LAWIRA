package heavynimbus.backend.mapper;

import heavynimbus.backend.db.command.Command;
import heavynimbus.backend.dto.command.CommandResponse;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public record CommandMapper(AttributeOptionMapper attributeOptionMapper) {
  public CommandResponse commandToCommandResponse(Command command) {
    Map<String, String> values = attributeOptionMapper.collectionToValues(command.getValues());
    return CommandResponse.builder()
        .id(command.getId())
        .quantity(command.getQuantity())
        .status(command.getStatus())
        .values(values)
        .build();
  }

}
