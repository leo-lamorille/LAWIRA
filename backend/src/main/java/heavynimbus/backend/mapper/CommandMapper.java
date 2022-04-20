package heavynimbus.backend.mapper;

import heavynimbus.backend.db.account.Account;
import heavynimbus.backend.db.attributeOption.AttributeOption;
import heavynimbus.backend.db.command.Command;
import heavynimbus.backend.db.command.CommandStatus;
import heavynimbus.backend.dto.command.CommandResponse;
import heavynimbus.backend.dto.command.CreateCommandRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public record CommandMapper(AttributeOptionMapper attributeOptionMapper) {

  public Command createCommandRequestToCommand(CreateCommandRequest createCommandRequest,
                                               Account account,
                                               List<AttributeOption> optionList){
    return Command.builder()
            .account(account)
            .status(CommandStatus.CREATED)
            .quantity(createCommandRequest.getQuantity())
            .values(optionList)
            .build();
  }

  public void updateCommand(Command command, List<AttributeOption> optionList, int quantity){
    command.setQuantity(quantity);
    command.setValues(optionList);
  }
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
