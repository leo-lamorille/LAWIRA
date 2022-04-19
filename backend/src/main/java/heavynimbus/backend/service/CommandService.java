package heavynimbus.backend.service;

import heavynimbus.backend.db.account.Account;
import heavynimbus.backend.db.attribute.AttributeRepository;
import heavynimbus.backend.db.attributeOption.AttributeOption;
import heavynimbus.backend.db.command.Command;
import heavynimbus.backend.db.command.CommandRepository;
import heavynimbus.backend.db.command.CommandStatus;
import heavynimbus.backend.dto.command.CommandResponse;
import heavynimbus.backend.dto.command.CreateCommandRequest;
import heavynimbus.backend.exception.BadRequestException;
import heavynimbus.backend.exception.NotFoundException;
import heavynimbus.backend.mapper.CommandMapper;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public record CommandService(CommandRepository commandRepository,
                             CommandMapper commandMapper,
                             AttributeOptionService attributeOptionService,
                             AttributeRepository attributeRepository, AccountService accountService) {
    public Command findCommandByIdAndAccountUsername(UUID id, String username) throws NotFoundException {
        return commandRepository.findByIdAndAccount_Username(id, username)
                .orElseThrow(() ->new NotFoundException("command", "id", id.toString()));
    }
    public List<CommandResponse> findAllByStatusAndAccountUsername(CommandStatus status, String username){
        status = status == null ? CommandStatus.CREATED : status;
        return commandRepository.findAllByAccount_UsernameAndStatus(username, status)
                .stream()
                .map(commandMapper::commandToCommandResponse)
                .collect(Collectors.toList());
    }

    public CommandResponse findByIdAndAccountUsername(UUID id, String username) throws NotFoundException {
        Command command = findCommandByIdAndAccountUsername(id, username);
        return commandMapper.commandToCommandResponse(command);
    }

    public CommandResponse createCommand(CreateCommandRequest createCommandRequest, Authentication authentication)
            throws NotFoundException, BadRequestException {
        if(createCommandRequest.getQuantity()<0) throw new BadRequestException("Command quantity must be positive");
        Account account = accountService.findByUsername(authentication.getName());
        List<AttributeOption> optionList = new ArrayList<>();
        for (UUID optionId: createCommandRequest.getOptions()) {
            AttributeOption option = attributeOptionService.findAttributeOptionById(optionId);

            List<String> alreadyPresentAttributes = optionList.stream().map(AttributeOption::getAttributeName).toList();
            if (alreadyPresentAttributes.contains(option.getAttributeName()))
                throw new BadRequestException("There are two options specifying " + option.getAttributeName());
            optionList.add(option);
        }

        long count = attributeRepository.count();
        if(optionList.size() != count) throw new BadRequestException(String.format("Should have %s options but have %s", count, optionList.size()));
        Command command = Command.builder()
                .account(account)
                .status(CommandStatus.CREATED)
                .quantity(createCommandRequest.getQuantity())
                .values(optionList)
                .build();
        command = commandRepository.save(command);
        return commandMapper.commandToCommandResponse(command);
    }

    public void deleteByIdAndAccountUsername(UUID id, String username) throws NotFoundException{
        Command command = findCommandByIdAndAccountUsername(id, username);
        commandRepository.delete(command);
    }
}