package heavynimbus.backend.service;

import heavynimbus.backend.db.attribute.Attribute;
import heavynimbus.backend.db.attribute.AttributeRepository;
import heavynimbus.backend.db.attributeOption.AttributeOption;
import heavynimbus.backend.db.attributeOption.AttributeOptionRepository;
import heavynimbus.backend.dto.attribute.CreateAttributeRequest;
import heavynimbus.backend.dto.attributeOption.AttributeOptionResponse;
import heavynimbus.backend.dto.attributeOption.CreateAttributeOptionRequest;
import heavynimbus.backend.exception.BadRequestException;
import heavynimbus.backend.exception.NotFoundException;
import heavynimbus.backend.mapper.AttributeOptionMapper;
import java.util.ArrayList;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AttributeOptionService {
  private final AttributeOptionRepository attributeOptionRepository;
  private final AttributeOptionMapper attributeOptionMapper;
  private final AttributeService attributeService;
  private final AttributeRepository attributeRepository;

  public AttributeOption findAttributeOptionByIdAndAttributeId(UUID optionId, UUID attributeId)
      throws NotFoundException {
    Attribute attribute = attributeService.findAttributeById(attributeId);
    return attribute.getOptions().stream()
        .filter(option -> optionId.toString().equals(option.getId()))
        .findAny()
        .orElseThrow(
            () -> new NotFoundException("Attribute option", "optionId", optionId.toString()));
  }

  public List<AttributeOption> findAllAttributeOptionByAttributeId(UUID attributeId)
      throws NotFoundException {
    Attribute attribute = attributeService.findAttributeById(attributeId);
    return attributeOptionRepository.findAllByAttribute(attribute);
  }

  public AttributeOption findAttributeOptionById(UUID id) throws NotFoundException {
    return attributeOptionRepository
        .findById(id.toString())
        .orElseThrow(() -> new NotFoundException("attribute option", "id", id.toString()));
  }

  public AttributeOptionResponse findByIdAndAttributeId(UUID id, UUID attributeId)
      throws NotFoundException {
    AttributeOption attributeOption = findAttributeOptionByIdAndAttributeId(id, attributeId);
    return attributeOptionMapper.attributeOptionToAttributeOptionResponse(attributeOption);
  }

  public List<AttributeOptionResponse> findAllByAttributeId(UUID attributeId)
      throws NotFoundException {
    return findAllAttributeOptionByAttributeId(attributeId).stream()
        .map(attributeOptionMapper::attributeOptionToAttributeOptionResponse)
        .toList();
  }

  public AttributeOptionResponse create(
      UUID attributeId, CreateAttributeOptionRequest createAttributeOptionRequest)
      throws NotFoundException {
    Attribute attribute = attributeService.findAttributeById(attributeId);
    AttributeOption option =
        attributeOptionMapper.createAttributeOptionRequestToAttributeOption(
            createAttributeOptionRequest);
    option.setAttribute(attribute);
    option = attributeOptionRepository.save(option);
    return attributeOptionMapper.attributeOptionToAttributeOptionResponse(option);
  }

  public AttributeOptionResponse update(
      UUID attributeId, UUID optionId, CreateAttributeOptionRequest createAttributeOptionRequest)
      throws NotFoundException {
    AttributeOption attributeOption = findAttributeOptionByIdAndAttributeId(optionId, attributeId);
    attributeOptionMapper.update(attributeOption, createAttributeOptionRequest);
    attributeOption = attributeOptionRepository.save(attributeOption);
    return attributeOptionMapper.attributeOptionToAttributeOptionResponse(attributeOption);
  }

  @Transactional
  public void delete(UUID attributeId, UUID optionId) throws NotFoundException {
    AttributeOption attributeOption = findAttributeOptionByIdAndAttributeId(optionId, attributeId);
    attributeOption.getAttribute().getOptions().remove(attributeOption);
    attributeOption.getCommands().clear();
    attributeOption.getConfigurations().clear();
    attributeOptionRepository.saveAndFlush(attributeOption);
    attributeOptionRepository.deleteById(attributeOption.getId());
  }

  public List<AttributeOption> checkAndGetOptions(Iterable<UUID> ids)
      throws BadRequestException, NotFoundException {
    List<AttributeOption> optionList = new ArrayList<>();
    for (UUID optionId : ids) {
      AttributeOption option = findAttributeOptionById(optionId);
      List<String> alreadyPresentAttributes =
          optionList.stream().map(AttributeOption::getAttributeName).toList();
      if (alreadyPresentAttributes.contains(option.getAttributeName()))
        throw new BadRequestException(
            "There are two options specifying " + option.getAttributeName());
      optionList.add(option);
    }
    return optionList;
  }
}
