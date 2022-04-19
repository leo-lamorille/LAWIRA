package heavynimbus.backend.service;

import heavynimbus.backend.db.command.CommandRepository;
import heavynimbus.backend.db.command.CommandStatus;
import heavynimbus.backend.dto.command.CommandResponse;
import heavynimbus.backend.mapper.CommandMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public record CommandService(CommandRepository commandRepository, CommandMapper commandMapper) {

    public List<CommandResponse> findAllByStatusAndAccountUsername(CommandStatus status, String username){
        status = status == null ? CommandStatus.CREATED : status;
        return commandRepository.findAllByAccount_UsernameAndStatus(username, status)
                .stream()
                .map(commandMapper::commandToCommandResponse)
                .collect(Collectors.toList());
    }
}
