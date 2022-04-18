package heavynimbus.backend.mapper;

import heavynimbus.backend.db.attributeOption.AttributeOption;
import heavynimbus.backend.dto.product.AttributeOptionResponse;
import org.springframework.stereotype.Component;

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
}
