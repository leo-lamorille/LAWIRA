package heavynimbus.backend.mapper;

import heavynimbus.backend.db.account.Account;
import heavynimbus.backend.db.attributeOption.AttributeOption;
import heavynimbus.backend.db.command.Command;
import heavynimbus.backend.db.command.CommandStatus;
import heavynimbus.backend.dto.command.AdminCommandResponse;
import heavynimbus.backend.dto.command.CommandResponse;
import heavynimbus.backend.dto.command.CreateCommandRequest;
import java.util.UUID;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public record CommandMapper(AttributeOptionMapper attributeOptionMapper) {

  public Command createCommandRequestToCommand(CreateCommandRequest createCommandRequest,
                                               Account account,
                                               List<AttributeOption> optionList){
    return Command.builder()
            .account(account)
            .status(CommandStatus.CREATED)
            .quantity(createCommandRequest.getQuantity())
            .options(optionList)
            .build();
  }

  public void updateCommand(Command command, List<AttributeOption> optionList, int quantity){
    command.setQuantity(quantity);
    command.setOptions(optionList);
  }
  public CommandResponse commandToCommandResponse(Command command) {
    return CommandResponse.builder()
        .id(UUID.fromString(command.getId()))
        .quantity(command.getQuantity())
        .status(command.getStatus())
        .options(command.getOptions()
            .stream()
            .map(attributeOptionMapper::attributeOptionToAttributeOptionDetailResponse)
            .toList())
        .build();
  }

  public AdminCommandResponse commandToAdminCommandResponse(Command command){
    return AdminCommandResponse.builder()
        .id(UUID.fromString(command.getId()))
        .username(command.getAccount().getUsername())
        .quantity(command.getQuantity())
        .status(command.getStatus())
        .options(command.getOptions()
          .stream()
          .map(attributeOptionMapper::attributeOptionToAttributeOptionDetailResponse)
          .toList())
        .build();
  }
}
