package heavynimbus.backend.service;

import heavynimbus.backend.db.UUIDBasedEntity;
import heavynimbus.backend.db.attributeOption.AttributeOption;
import heavynimbus.backend.db.command.Command;
import heavynimbus.backend.db.command.CommandRepository;
import heavynimbus.backend.db.command.CommandStatus;
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

  public List<AttributeOptionStatsResponse> getCommandOptionStatsByAttribute(UUID attributeId)
      throws NotFoundException {
    Map<AttributeOption, Double> res = new HashMap<>();
    List<AttributeOption> options =
        attributeOptionService.findAllAttributeOptionByAttributeId(attributeId);
    options.forEach(option -> res.put(option, 0D));
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
        .forEach(option -> res.put(option, res.get(option) + 1D));
    /*
    .forEach(
        command ->
            command.getOptions().stream()
                .map(
                    option -> {
                      System.out.println("option.getId() = " + option.getId());
                      res.keySet().stream()
                          .map(UUIDBasedEntity::getId)
                          .map(s -> s + "\t")
                          .forEach(System.out::print);
                      System.out.println();
                      return res.keySet().stream()
                          .filter(o -> o.getId().equals(option.getId()))
                          .findAny();
                    })
                .filter(Optional::isPresent)
                .map(Optional::get)
                .forEach(option -> res.put(option, res.get(option) + 1D)));*/
    return res.keySet().stream()
        //.peek(e -> System.out.printf("%s %s %s%n", e.getValue(), res.get(e)))
        .map(
            option ->
                AttributeOptionStatsResponse.builder()
                    .optionType(option.getType())
                    .optionValue(option.getValue())
                    .occurences(res.get(option))
                    .build())
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
