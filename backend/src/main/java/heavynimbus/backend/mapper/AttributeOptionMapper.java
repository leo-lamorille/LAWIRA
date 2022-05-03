package heavynimbus.backend.mapper;

import heavynimbus.backend.db.attributeOption.AttributeOption;
import heavynimbus.backend.dto.attributeOption.AttributeOptionDetailResponse;
import heavynimbus.backend.dto.attributeOption.AttributeOptionResponse;
import heavynimbus.backend.dto.attributeOption.CreateAttributeOptionRequest;
import java.util.UUID;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class AttributeOptionMapper {

  public AttributeOption createAttributeOptionRequestToAttributeOption(
      CreateAttributeOptionRequest createAttributeOptionRequest) {
    return AttributeOption.builder()
        .type(createAttributeOptionRequest.getType())
        .value(createAttributeOptionRequest.getValue())
        .build();
  }

  public void update(
      AttributeOption attributeOption, CreateAttributeOptionRequest createAttributeOptionRequest) {
    attributeOption.setType(createAttributeOptionRequest.getType());
    attributeOption.setValue(createAttributeOptionRequest.getValue());
  }

  public AttributeOptionResponse attributeOptionToAttributeOptionResponse(
      AttributeOption attributeOption) {
    return AttributeOptionResponse.builder()
        .id(UUID.fromString(attributeOption.getId()))
        .type(attributeOption.getType())
        .value(attributeOption.getValue())
        .build();
  }

  public AttributeOptionDetailResponse attributeOptionToAttributeOptionDetailResponse(
      AttributeOption attributeOption) {
    return AttributeOptionDetailResponse.builder()
        .attributeId(UUID.fromString(attributeOption.getAttribute().getId()))
        .attributeName(attributeOption.getAttribute().getName())
        .optionId(UUID.fromString(attributeOption.getId()))
        .optionType(attributeOption.getType())
        .optionValue(attributeOption.getValue())
        .build();
  }

  public Map<String, String> collectionToValues(Collection<AttributeOption> attributeOptions) {
    return attributeOptions.stream()
        .collect(Collectors.toMap(AttributeOption::getAttributeName, AttributeOption::getValue));
  }
}
