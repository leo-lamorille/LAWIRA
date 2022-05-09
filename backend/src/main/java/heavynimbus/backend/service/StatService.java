package heavynimbus.backend.service;

import heavynimbus.backend.db.UUIDBasedEntity;
import heavynimbus.backend.db.attributeOption.AttributeOption;
import heavynimbus.backend.db.command.Command;
import heavynimbus.backend.db.command.CommandRepository;
import heavynimbus.backend.db.command.CommandStatus;
import heavynimbus.backend.db.configuration.Configuration;
import heavynimbus.backend.db.configuration.ConfigurationRepository;
import heavynimbus.backend.db.stats.AttributeOptionStatsResponse;
import heavynimbus.backend.exception.NotFoundException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StatService {
  private final AttributeOptionService attributeOptionService;
  private final CommandRepository commandRepository;
  private final ConfigurationRepository configurationRepository;

  public List<AttributeOptionStatsResponse> getCommandOptionStatsByAttribute(UUID attributeId)
      throws NotFoundException {
    Map<AttributeOption, Double> commandStats = new HashMap<>();
    Map<AttributeOption, Double> configurationStats = new HashMap<>();
    List<AttributeOption> options =
        attributeOptionService.findAllAttributeOptionByAttributeId(attributeId);
    options.stream()
        .peek(option -> configurationStats.put(option, 0D))
        .forEach(option -> commandStats.put(option, 0D));
    configurationRepository.findAll().stream()
        .map(Configuration::getOptions)
        .flatMap(Collection::stream)
        .map(option -> options.stream().filter(o -> o.getId().equals(option.getId())).findAny())
        .filter(Optional::isPresent)
        .map(Optional::get)
        .forEach(option -> configurationStats.put(option, configurationStats.get(option) + 1D));
    commandRepository.findAll().stream()
        .filter(
            command ->
                CommandStatus.DONE.equals(command.getStatus())
                    || CommandStatus.PENDING.equals(command.getStatus()))
        .map(Command::getOptions)
        .flatMap(Collection::stream)
        .map(option -> options.stream().filter(o -> o.getId().equals(option.getId())).findAny())
        .filter(Optional::isPresent)
        .map(Optional::get)
        .forEach(option -> commandStats.put(option, commandStats.get(option) + 1D));
    return commandStats.keySet().stream()
        .map(
            option -> {
              var configurationOccurrences = configurationStats.get(option);
              return AttributeOptionStatsResponse.builder()
                  .optionType(option.getType())
                  .optionValue(option.getValue())
                  .commandOccurrences(commandStats.get(option))
                  .configurationOccurrences(configurationOccurrences)
                  .build();
            })
        .toList();
  }

  public List<AttributeOptionStatsResponse> getOptionStatsByAttribute(UUID attributeId)
      throws NotFoundException {
    List<AttributeOption> attributeOptions =
        attributeOptionService.findAllAttributeOptionByAttributeId(attributeId);
    return attributeOptions.stream()
        .map(
            option ->
                AttributeOptionStatsResponse.builder()
                    .optionValue(option.getValue())
                    .optionType(option.getType())
                    .build())
        .toList();
  }
}
