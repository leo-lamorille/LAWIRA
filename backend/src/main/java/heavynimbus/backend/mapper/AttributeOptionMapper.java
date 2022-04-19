package heavynimbus.backend.mapper;

import heavynimbus.backend.db.attributeOption.AttributeOption;
import heavynimbus.backend.dto.product.AttributeOptionResponse;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class AttributeOptionMapper {
  public AttributeOptionResponse attributeOptionToAttributeOptionResponse(
      AttributeOption attributeOption) {
    return AttributeOptionResponse.builder()
        .id(attributeOption.getId())
        .type(attributeOption.getType())
        .value(attributeOption.getValue())
        .build();
  }

  public Map<String, String> collectionToValues(Collection<AttributeOption> attributeOptions) {
    return attributeOptions.stream()
        .collect(Collectors.toMap(AttributeOption::getAttributeName, AttributeOption::getValue));
  }
}
